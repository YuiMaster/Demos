package cn.yui.ipclib.core;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import cn.yui.ipclib.ServiceManager;

public class BinderProxy implements InvocationHandler {
    @NonNull
    private final Class clazz;
    @NonNull
    private static final Gson gson = new Gson();

    public BinderProxy(@NonNull final Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String json = BinderIPC.getInstance().sendRequest(clazz, method,
                args, ServiceManager.TYPE_INVOKE);
        if (TextUtils.isEmpty(json)) {
            return null;
        } else {
            return gson.fromJson(json, method.getReturnType());
        }
    }
}
