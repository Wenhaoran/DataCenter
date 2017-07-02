package cn.digitalpublishing.springmvc.form;

import java.io.Serializable;

/**
 * @description：操作结果集
 * @author：shurui.chen
 * @date：2015/10/1 14:51
 */
public class Result implements Serializable {

    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;

    private static final long serialVersionUID = 5576237395711742681L;

    private boolean success = false;

    private String msg = "";

    private Object obj = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

	public Result() {
	}

	public Result(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

	public Result(boolean success, Object obj) {
		this.success = success;
		this.obj = obj;
	}

}
