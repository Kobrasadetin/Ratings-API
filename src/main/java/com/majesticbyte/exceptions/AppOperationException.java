package com.majesticbyte.exceptions;

public class AppOperationException extends Exception {
    public AppOperationException(ExceptionMessage message) {
        super(message.getError());
    }
}

