package com.ruso.zapicito.exception;

public class ZapicitoException extends Exception{
    public ZapicitoException(String message) {
        super(message);
    }

    public ZapicitoException(String message, Throwable cause) {
        super(message, cause);
    }
}
