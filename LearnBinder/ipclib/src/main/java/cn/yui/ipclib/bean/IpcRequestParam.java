package cn.yui.ipclib.bean;

public class IpcRequestParam {
    /**
     * 类名
     */
    private String paramClassName;
    /**
     * 值
     */
    private String paramValue;

    public IpcRequestParam() {

    }

    public IpcRequestParam(String paramClassName, String paramValue) {
        this.paramClassName = paramClassName;
        this.paramValue = paramValue;
    }

    public String getParamClassName() {
        return paramClassName;
    }

    public void setParamClassName(String paramClassName) {
        this.paramClassName = paramClassName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}
