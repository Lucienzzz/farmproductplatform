package com.zzq.farmproductplatform.common;

import lombok.Data;

/**
 * @author Moore
 * @since 2021/04/06
 */
@Data
public class JsonResult<T> {

    private boolean success;

    private T data;

    private Integer errorCode;

    private String errorMsg;

    public static <T> JsonResult<T> ok() {
        return ok(null);
    }

    public static <T> JsonResult<T> ok(T data) {
        JsonResult<T> result = new JsonResult<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> JsonResult<T> error() {
        JsonResult<T> result = new JsonResult<>();
        result.setErrorCode(500);
        result.setErrorMsg("服务异常");
        return result;
    }
}
