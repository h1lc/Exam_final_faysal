package com.example.methodo_de_test_eval.exception;


public class DataIntegrityViolationException extends RuntimeException {


    public DataIntegrityViolationException(String message) {
        super(message);
    }


    public DataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }


    public DataIntegrityViolationException(Throwable cause) {
        super(cause);
    }
} 