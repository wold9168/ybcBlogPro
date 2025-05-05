package com.hgd.util;

import com.hgd.http.HttpCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code;
    private String msg;
    private Object data;

    public static Result ok(){
        return new Result(HttpCode.SUCCESS.getCode(),HttpCode.SUCCESS.getMsg(),null);
    }
    public static Result ok(Object data){
        return new Result(HttpCode.SUCCESS.getCode(),HttpCode.SUCCESS.getMsg(),data);
    }
    public static Result fail(){
        return new Result(HttpCode.SYSTEM_FAIL.getCode(),HttpCode.SYSTEM_FAIL.getMsg(),null);
    }
    public static Result fail(HttpCode httpCode){
        return new Result(httpCode.getCode(),httpCode.getMsg(),null);
    }
}
