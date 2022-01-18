package com.stockexchange.exchange.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response implements Serializable {

    private String message;
    private double data;
    private ResponseStatus status;
}
