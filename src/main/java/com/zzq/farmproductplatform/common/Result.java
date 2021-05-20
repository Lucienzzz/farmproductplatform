package com.zzq.farmproductplatform.common;


import lombok.Data;

@Data
public class Result {
    private int status;
    private Object data;
    private String msg;
    public static Result ok(Object data) {
        Result result = new Result();
        result.setStatus(Const.OK);
        result.setMsg(Const.OK_MSG);
        result.setData(data);
        return result;
    }
    public static Result error(Object data, String msg, int status) {
        Result result = new Result();
        result.setData(data);
        result.setMsg(msg);
        result.setStatus(status);
        return result;
    }
}
