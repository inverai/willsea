package com.example.willsea.exception;

/**
 * Created by yt on 2018/6/25.
 */
public class SubException extends RuntimeException {
    public SubException() {
    }

    public SubException(String message) {
        super(message);
    }

    public SubException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubException(Throwable cause) {
        super(cause);
    }
}
