package org.zxc.common.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一结果返回类
 */
@Data
public class Result implements Serializable {

    private int code; // 状态码，200：成功，400：异常
    private String msg; // 消息
    private Object data; // 数据，直接用 Object 来声明


    /**
     * 操作成功的静态方法
     * @param data
     * @return
     */
    public static Result success(Object data) {
        return success(200, "操作成功", data);
    }

    /**
     * 操作成功的静态方法，用于封装数据
     * @param code 状态码
     * @param msg 消息
     * @param data 数据
     * @return
     */
    public static Result success(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result fail(String msg) {
        return fail(400, msg, null);
    }

    public static Result fail(String msg, Object data) {
        return fail(400, msg, data);
    }

    /**
     * 操作失败的静态方法，除了方法名，其他与操作成功的静态方法基本一样
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Result fail(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

}
