package cn.yui.ipclib;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.yui.ipclib.bean.IpcRequestBean;
import cn.yui.ipclib.bean.IpcRequestParam;
import cn.yui.ipclib.cache.CacheCenter;

/**
 * @Description: java类作用描述
 * @Author: Yui Master
 * @CreateDate: 2022/1/22 11:06 上午
 */
public class ServiceManager extends Service {
    @NonNull
    private final CacheCenter cacheCenter = CacheCenter.getInstance();


    /**
     * 实例化对象
     */
    public static final int TYPE_GET = 1;
    /**
     * 服务调用
     */
    public static final int TYPE_INVOKE = 2;

    @NonNull
    private final Gson gson = new Gson();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BinderInterface.Stub() {
            @Override
            public String request(String string) throws RemoteException {
                IpcRequestBean bean = gson.fromJson(string, IpcRequestBean.class);
                int type = bean.getType();
                switch (type) {
                    case TYPE_GET: {
                        Method method = cacheCenter.getMethod(bean);
                        Object[] parameters = makeParameterObject(bean);
                        if (method != null) {
                            try {
                                Object object = method.invoke(null, parameters);
                                if (object != null) {
                                    cacheCenter.putObject(bean.getClassName(), object);
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                    case TYPE_INVOKE: {
                        Object object = cacheCenter.getObject(bean.getClassName());
                        Method tempMethod = cacheCenter.getMethod(bean);
                        Object[] mParameters = makeParameterObject(bean);
                        try {
                            Object result = tempMethod.invoke(object, mParameters);
                            String data = gson.toJson(result);
                            return data;
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                return null;
            }
        };
    }


    private Object[] makeParameterObject(IpcRequestBean bean) {
        Object[] mParameters = null;
        IpcRequestParam[] requestParams = bean.getRequestParams();
        if (requestParams != null && requestParams.length > 0) {
            mParameters = new Object[requestParams.length];
            for (int i = 0; i < requestParams.length; i++) {
                IpcRequestParam param = requestParams[i];
                Class<?> clazz = cacheCenter.getClassType(param.getParamClassName());
                // clazz   value  object
                mParameters[i] = gson.fromJson(param.getParamValue(), clazz);
            }

        } else {
            mParameters = new Object[0];
        }
        return mParameters;
    }
}
