package com.stockexchange.exchange.util;

import com.stockexchange.exchange.domain.StockTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Transaction {

    INSTANCE;

    private Transaction(){}

    private List<StockTransaction> stockTransactionList = new ArrayList<>();

    public List<StockTransaction> getStockTransactionList() {
        return stockTransactionList;
    }

    public void addTransaction(StockTransaction  stockTransaction) {
        this.stockTransactionList.add( stockTransaction);
        Collections.sort(this.stockTransactionList);
        System.out.println(stockTransactionList.toString());
    }
}
