package com.majesticbyte.exceptions;

public class GroupLimitReachedException extends AppConstraintException {
    public GroupLimitReachedException(String message){
        super(message);
    }
}
