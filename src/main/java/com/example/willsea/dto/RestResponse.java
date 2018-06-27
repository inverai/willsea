package com.example.willsea.dto;

/**
 * Created by yt on 2018/6/26.
 */


/**
 * 封装JSON结果
  */

public class RestResponse<T> {

    private boolean success;

    private T data;

    private String error;


    public RestResponse(boolean success) {
        this.success = success;
    }

    public RestResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public RestResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static RestResponse ok() {
        return new RestResponse(true);
    }

    public static <T> RestResponse ok(T data) {
        return new RestResponse(true, data);
    }

    public static RestResponse fail() {
        return new RestResponse(false);
    }

    public static RestResponse fail(String message) {
        return new RestResponse(false, message);
    }
}
