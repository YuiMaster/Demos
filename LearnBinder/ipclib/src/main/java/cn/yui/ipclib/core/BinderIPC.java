package cn.yui.ipclib.core;

import static cn.yui.ipclib.ServiceManager.TYPE_GET;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.yui.ipclib.BinderInterface;
import cn.yui.ipclib.ClassId;
import cn.yui.ipclib.ServiceManager;
import cn.yui.ipclib.bean.IpcRequestBean;
import cn.yui.ipclib.bean.IpcRequestParam;
import cn.yui.ipclib.cache.CacheCenter;

public class BinderIPC {
    /**
     * 主进程
     */
    @NonNull
    private final CacheCenter cacheCenter = CacheCenter.getInstance();
    @NonNull
    private static final BinderIPC instance = new BinderIPC();

    @NonNull
    private static final Gson gson = new Gson();
    /***
     * application context
     */
    private Context mContext;
    @Nullable
    private BinderInterface binderInterface;

    private BinderIPC() {

    }

    @NonNull
    public static BinderIPC getInstance() {
        return instance;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 服务注册
     */
    public void register(Class<?> clazz) {
        cacheCenter.register(clazz);
    }

    public <T> T getInstance(Class<T> clazz, Object... params) {
        sendRequest(clazz, null, params, TYPE_GET);
        return getProxy(clazz);
    }

    /***
     * 反射调用初始化
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T getProxy(Class<T> clazz) {
        ClassLoader classLoader = mContext.getClassLoader();
        return (T) Proxy.newProxyInstance(classLoader, new Class[]{clazz}, new BinderProxy(clazz));
    }

    /**
     * 发送数据
     *
     * @param clazz
     * @param method
     * @param parameters
     * @param type
     * @return
     */
    public <T> String sendRequest(Class<T> clazz, Method method, Object[] parameters, int type) {
        // 取得标记的类名
        String className = clazz.getAnnotation(ClassId.class).value();
        String methodName = method == null ? "getInstance" : method.getName();

        IpcRequestParam[] requestParams = null;
        if (parameters != null && parameters.length > 0) {
            requestParams = new IpcRequestParam[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];
                String parameterClassName = parameter.getClass().getName();
                String parameterValue = gson.toJson(parameter);
                IpcRequestParam requestParam = new IpcRequestParam(parameterClassName, parameterValue);
                requestParams[i] = requestParam;
            }
        }

        // 数据类转json字符串
        IpcRequestBean requestBean = new IpcRequestBean(type, className, methodName, requestParams);
        String json = gson.toJson(requestBean);


        String response = null;
        try {
            if (binderInterface != null) {
                response = binderInterface.request(json);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void open(Context context) {
        open(context, null);
    }

    public void open(Context context, String packageName) {
        init(context);
        bind(context.getApplicationContext(), packageName, ServiceManager.class);
    }

    /**
     * 通过系统bind发送数据，以达到跨进程通信
     */
    public void bind(Context context, String packageName, Class<? extends ServiceManager> service) {
        Intent intent = null;
        if (TextUtils.isEmpty(packageName)) {
            intent = new Intent(context, service);
        } else {
            ComponentName component = new ComponentName(packageName, service.getName());
            intent = new Intent();
            intent.setComponent(component);
            intent.setAction(service.getName());
        }
        BinderServiceConnection serviceConnection = new BinderServiceConnection();
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }


    private class BinderServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binderInterface = BinderInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
