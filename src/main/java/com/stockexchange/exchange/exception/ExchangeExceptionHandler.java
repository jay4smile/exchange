package com.stockexchange.exchange.exception;

import com.stockexchange.exchange.domain.Response;
import com.stockexchange.exchange.domain.ResponseStatus;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExchangeExceptionHandler {

    @ExceptionHandler(value = NoStockFoundException.class)
    public ResponseEntity<Response> handleNoStockFoundException() {
        Response apiError = new Response();
        apiError.setMessage("No Stock Details Found");
        apiError.setStatus(ResponseStatus.FAIL);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidDataFoundException.class)
    public ResponseEntity<Response> handleInvalidRequestException() {
        Response apiError = new Response();
        apiError.setMessage("Invalid request");
        apiError.setStatus(ResponseStatus.FAIL);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
