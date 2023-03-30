package com.mendes.library.service.exception;

public class DataIntegrityViolationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataIntegrityViolationException(String s) {
        super(s);
    }
}
