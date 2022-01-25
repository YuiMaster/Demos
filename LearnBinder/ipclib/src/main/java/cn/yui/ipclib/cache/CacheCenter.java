package cn.yui.ipclib.cache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import cn.yui.ipclib.bean.IpcRequestBean;
import cn.yui.ipclib.bean.IpcRequestParam;

/**
 * @Description: java类作用描述
 * @Author: Yui Master
 * @CreateDate: 2022/1/22 11:07 上午
 */
public class CacheCenter {
    /**
     * 类名表
     */
    @NonNull
    private final ConcurrentHashMap<String, Class<?>> mClassMap;
    /***
     * 方法表
     */
    @NonNull
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Method>> mAllMethodMap;
    /**
     * 类表
     */
    @NonNull
    private final ConcurrentHashMap<String, Object> mInstanceObjectMap;

    @NonNull
    private static final CacheCenter instance = new CacheCenter();

    @NonNull
    public static CacheCenter getInstance() {
        return instance;
    }

    private CacheCenter() {
        mClassMap = new ConcurrentHashMap<>();
        mAllMethodMap = new ConcurrentHashMap<>();
        mInstanceObjectMap = new ConcurrentHashMap<>();
    }

    public void putObject(@NonNull String className, @NonNull Object instance) {
        mInstanceObjectMap.put(className, instance);
    }

    public Object getObject(@NonNull String className) {
        return mInstanceObjectMap.get(className);
    }

    @Nullable
    public Method getMethod(@NonNull IpcRequestBean requestBean) {
        ConcurrentHashMap<String, Method> map = mAllMethodMap.get(requestBean.getClassName());
        if (map == null) {
            return null;
        } else {
            String key = getMethodParams(requestBean);
            return map.get(key);
        }
    }


    /**
     * 注册
     */
    public void register(@NonNull Class<?> clazz) {
        registerClass(clazz);
        registerMethod(clazz);
    }


    /**
     * 注册类
     */
    private void registerClass(@NonNull Class<?> clazz) {
        String className = clazz.getName();
        mClassMap.put(className, clazz);
    }

    /**
     * 注册类里的函数
     * 通过注解取得
     */
    private void registerMethod(@NonNull Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            ConcurrentHashMap<String, Method> map = mAllMethodMap.get(clazz.getName());
            if (map == null) {
                map = new ConcurrentHashMap<>();
                mAllMethodMap.put(clazz.getName(), map);
            }
            String key = getMethodParam(method);
            map.put(key, method);
        }
    }

    /**
     * 根据完整的包路径，取得类
     *
     * @param parameterClassName
     * @return
     */
    public Class<?> getClassType(@NonNull String parameterClassName) {
        try {
            Class clazz = Class.forName(parameterClassName);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得函数参数
     *
     * @param bean 发送的数据
     * @return
     */
    public String getMethodParams(@NonNull IpcRequestBean bean) {
        StringBuilder result = new StringBuilder();
        result.append(bean.getMethodName());
        if (bean.getRequestParams() == null || bean.getRequestParams().length == 0) {
            return result.toString();
        }
        int length = bean.getRequestParams().length;
        IpcRequestParam[] params = bean.getRequestParams();
        for (int i = 0; i < length; ++i) {
            result.append("-").append(params[i].getParamClassName());
        }

        return result.toString();
    }

    /**
     * 拼接函数名和参数,生成方法签名
     *
     * @param method 函数
     * @return 方法签名
     */
    private String getMethodParam(@NonNull Method method) {
        StringBuilder result = new StringBuilder();
        result.append(method.getName());
        Class<?>[] paramsType = method.getParameterTypes();
        if (paramsType.length > 0) {
            for (Class<?> aClass : paramsType) {
                result.append("-").append(aClass.getName());
            }
        }
        return result.toString();
    }
}
