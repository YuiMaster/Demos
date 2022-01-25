package cn.yui.ipclib.bean;

/**
 * @Description: java类作用描述
 * @Author: Yui Master
 * @CreateDate: 2022/1/22 11:33 上午
 */
public class IpcRequestBean {
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /***
     * 1 实例化对象 2 调用方法
     */
    private int type;
    /***
     * 参数
     */
    private IpcRequestParam[] requestParams;

    public IpcRequestBean() {

    }

    public IpcRequestBean(int type, String className, String methodName, IpcRequestParam[] requestParams) {
        this.className = className;
        this.methodName = methodName;
        this.type = type;
        this.requestParams = requestParams;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public IpcRequestParam[] getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(IpcRequestParam[] requestParams) {
        this.requestParams = requestParams;
    }
}
