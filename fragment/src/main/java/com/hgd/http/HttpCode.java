package com.hgd.http;

import lombok.Data;

public enum HttpCode {
    SUCCESS(200,"操作成功"),RELOGIN(401,"重新登录"),SYSTEM_FAIL(500,"系统错误"),USERNAME_PASSWORD_FAIL(501, "用户名或密码错误");;
    private int code;
    private String msg;
    HttpCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public void setCode(int code) {
        this.code = code;
    }
}
