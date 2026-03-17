package com.chad.sales.exception;

public class VendaNotFoundException extends RuntimeException {
    public VendaNotFoundException(String message) {
        super(message);
    }
}