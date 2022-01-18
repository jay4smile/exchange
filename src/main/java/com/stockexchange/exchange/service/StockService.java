package com.stockexchange.exchange.service;


import com.stockexchange.exchange.domain.*;
import com.stockexchange.exchange.exception.NoStockFoundException;
import com.stockexchange.exchange.util.StockInitializer;
import com.stockexchange.exchange.util.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Log4j2
@Service
public class StockService {

    @Autowired
    private StockInitializer stockInitializer;

    public Response calculateDividend(String stockName, double price) {

        double dividend = 0.0;
        Response response = new Response();

        Optional<Stock> optionalStock = checkStockPresence(stockName);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            if (StockType.COMMON.equals(stock.getType())) {
                dividend = stock.getLastDividend() / price;
            } else if (StockType.PREFERRED.equals(stock.getType())) {
                dividend = ((stock.getFixedDividend()/100) * stock.getParValue())/price;
            }

            response.setData(dividend);
            response.setStatus(ResponseStatus.SUCCESS);
            response.setMessage("Dividend Yield");
        } else {
            throw new NoStockFoundException();
        }
        return response;
    }


    private Optional<Stock> checkStockPresence(String stockName) {
        Set<Stock> stockDetails = stockInitializer.getStockSet();
        return stockDetails.stream()
                .filter(stockObj -> stockName.toUpperCase().equals( stockObj.getStock()))
                .findAny();
    }

    public Response calculatePnERation(String stockName, double price) {
        double ratio = 0.0;
        Response response = new Response();

        Optional<Stock> optionalStock = checkStockPresence(stockName);
        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            if (null != stock.getLastDividend() && 0 != stock.getLastDividend()) {
                ratio = price/ stock.getLastDividend();
            } else {
                ratio = 0;
            }

           response.setStatus(ResponseStatus.SUCCESS);
           response.setData(ratio);
           response.setMessage("P&E ratio");

        } else {
            throw new NoStockFoundException();
        }
        return response;
    }

    public Response submitTransaction(StockTransaction stockTransaction) {
        Optional<Stock> optionalStock = checkStockPresence(stockTransaction.getStockName());
        Response response = new Response();
        if (optionalStock.isPresent()) {
            stockTransaction.setTransactionTime(LocalDateTime.now());
            stockInitializer.addTransaction(stockTransaction);
            response.setStatus(ResponseStatus.SUCCESS);
            response.setMessage("Transaction placed");
        } else {
            throw new NoStockFoundException();
        }
        return response;
    }

    public Response calculateVolumeWeight(String stockName) {
        Response response = new Response();

        final LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(15);
        log.info("Local Date Time {}", localDateTime);

        Predicate<StockTransaction> namePredicate = new Predicate<StockTransaction>() {
            @Override
            public boolean test(StockTransaction stockTransaction) {
                return stockName.toUpperCase().equals(stockTransaction.getStockName());
            }
        };

        Predicate<StockTransaction> dateTimePredicate = new Predicate<StockTransaction>() {
            @Override
            public boolean test(StockTransaction stockTransaction) {
                return stockTransaction.getTransactionTime().isAfter(localDateTime);
            }
        };

        Optional<Stock> optionalStock = checkStockPresence(stockName);
        if (optionalStock.isPresent()) {

            List<StockTransaction> transactionList = Transaction.INSTANCE.getStockTransactionList();

            if (!transactionList.isEmpty()) {

                List<StockTransaction> finalList = transactionList
                        .stream()
                        .filter(dateTimePredicate)
                        .filter(namePredicate)
                        .collect(Collectors.toList());

                int sumOfQuantity = 0;
                double sumOftradedPriceAndQuantity = 0.0;

                if (!finalList.isEmpty()) {
                    for(StockTransaction transaction: finalList) {
                        sumOftradedPriceAndQuantity += (transaction.getQuantity() * transaction.getPrice());
                        sumOfQuantity += transaction.getQuantity();
                    }

                    response.setData(sumOftradedPriceAndQuantity/sumOfQuantity);
                    response.setMessage("Volume Weighted Stock Price");
                    response.setStatus(ResponseStatus.SUCCESS);

                } else {
                    response.setData(0);
                    response.setMessage("Volume Weighted Stock Price");
                    response.setStatus(ResponseStatus.SUCCESS);
                }

            } else {
                response.setData(0);
                response.setMessage("Volume Weighted Stock Price");
                response.setStatus(ResponseStatus.SUCCESS);
            }

        } else {
            throw new NoStockFoundException();
        }
        return response;
    }

    public Response calculateGeomatricMean() {
        Response response = new Response();
        response.setMessage("All Share Index");
        List<StockTransaction> transactionList = Transaction.INSTANCE.getStockTransactionList();
        if (!transactionList.isEmpty()) {
            double sum =0.0;
            for(StockTransaction transaction: transactionList) {
                sum *= transaction.getPrice();
            }

            response.setData(Math.pow(sum, 1 / transactionList.size()));
            response.setStatus(ResponseStatus.SUCCESS);

        } else {
            response.setData(0);
            response.setStatus(ResponseStatus.FAIL);
        }

        return response;
    }
}
