package com.oa.config;

/**
 * 响应模型
 * @author zhengwen
 * @since 2018年10月21日11:44:22
 */
import java.io.Serializable;

public class ResponseModel<T> implements Serializable {
    private static final long serialVersionUID = -1241360949457314497L;
    private int status;
    private T result;
    private String message;
    private String code;

    public ResponseModel() {
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String toString() {
        return "ResponseModel [status=" + this.status + ", result=" + this.result +  ", message=" + this.message + ", code=" + this.code + "]";
    }
}
