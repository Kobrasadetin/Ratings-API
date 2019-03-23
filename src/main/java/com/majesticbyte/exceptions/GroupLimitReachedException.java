package com.majesticbyte.exceptions;

public class GroupLimitReachedException extends AppOperationException {
    public GroupLimitReachedException(ExceptionMessage message){
        super(message);
    }
}
