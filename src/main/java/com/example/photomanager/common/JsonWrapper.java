package com.example.photomanager.common;


import lombok.Data;

/**
 * @author alex
 */

@Data
public class JsonWrapper<T> {

    private static final long serialVersionUID = 1L;
    private static final int SUCCESS_CODE = 0;
    private static final String SUCCESS_MESSAGE = "success";

    /**
     * 状态码  == 0 成功
     * > 0 服务器内部异常
     * < 0 外部异常，由调用方处理
     */
    private int code;

    /**
     * 返回API调用情况
     */
    private String msg;

    /**
     * 返回的数据
     */
    private T data;

    private JsonWrapper(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonWrapper(int code, String msg) {
        this(code, msg, null);
    }

    public JsonWrapper(T data) {
        this(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

}
