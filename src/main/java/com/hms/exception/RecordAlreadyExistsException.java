package com.hms.exception;

public class RecordAlreadyExistsException extends RuntimeException{
    public RecordAlreadyExistsException(String msg) {
        super(msg);
    }
}
