package com.just.study.utils;

import com.just.study.constant.CodeState;
import lombok.Data;

@Data
public class Response {

    public Response(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;
    private Object data;

    public static Response success(Integer code, String msg, Object data) {
        return new Response(code, msg, data);
    }

    public static Response success(String msg, Object data) {
        return new Response("200", msg, data);
    }

    public static Response success(Object data) {
        return new Response(200, "操作成功", data);
    }

    public static Response error(Integer code, String msg, Object data) {
        return new Response(code, msg, data);
    }

    public static Response error(String msg) {
        return new Response(500, msg);
    }

    public static Response error() {
        return new Response(500, "操作失败");
    }


}
