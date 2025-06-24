package com.example.methodo_de_test_eval.exception;

public class ObjectNotFoundException extends RuntimeException {


    public ObjectNotFoundException(String message) {
        super(message);
    }


    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


    public ObjectNotFoundException(Throwable cause) {
        super(cause);
    }
} 