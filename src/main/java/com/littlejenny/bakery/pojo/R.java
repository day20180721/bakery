package com.littlejenny.bakery.pojo;

import lombok.Data;

@Data
public class R {
    public static R ok = new R(200, "");
    public static R error = new R(404, "");
    private Integer code;
    private String msg;
    private Object body;

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(Integer code, Object body) {
        this.code = code;
        this.body = body;
    }

    public static R error(String msg) {
        return new R(404, msg);
    }

    public static R error(Object body) {
        return new R(404, body);
    }
    public static R ok(String msg) {
        return new R(200, msg);
    }
    public static R ok(Object body) {
        return new R(200, body);
    }
}
