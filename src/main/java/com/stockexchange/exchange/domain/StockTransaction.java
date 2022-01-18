package com.stockexchange.exchange.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ToString
public class StockTransaction implements Comparable<StockTransaction>{
    private String stockName;
    private Integer quantity;
    private Double price;
    private TransactionType transactionType;
    private LocalDateTime transactionTime;

    @Override
    public int compareTo(StockTransaction o) {
        return this.getTransactionTime().compareTo(o.getTransactionTime());
    }
}
