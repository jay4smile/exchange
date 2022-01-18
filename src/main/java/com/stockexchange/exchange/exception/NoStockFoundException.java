package com.stockexchange.exchange.exception;

public class NoStockFoundException extends RuntimeException {
    public NoStockFoundException() {
    }

    public NoStockFoundException(String message) {
        super(message);
    }

    public NoStockFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
