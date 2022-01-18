package com.stockexchange.exchange.exception;

public class InvalidDataFoundException extends RuntimeException {
    public InvalidDataFoundException() {
    }

    public InvalidDataFoundException(String message) {
        super(message);
    }

    public InvalidDataFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
