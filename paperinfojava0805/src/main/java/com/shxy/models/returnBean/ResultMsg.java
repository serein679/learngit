package com.shxy.models.returnBean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Lenl
 * @version v1.0
 * @create 2022-03-27 20:45
 * @description 每个接口返回响应的结构
 */
@Data
public class ResultMsg<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    private boolean success;

    /**
     * 描述，出错时返回错误信息
     */
    private String message;

    /**
     * 返回响应码
     */
    private Integer code;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 结果对象
     */
    private T data;

    public ResultMsg(boolean success, String message, Integer code, T data) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultMsg{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }
}
