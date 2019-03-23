package com.majesticbyte.exceptions;

public class UnauthorizedOperationException extends AppOperationException {
    public UnauthorizedOperationException(ExceptionMessage message){
        super(message);
    }
    public UnauthorizedOperationException(){
        super(new ExceptionMessage("operation not authorized"));
    }
}
