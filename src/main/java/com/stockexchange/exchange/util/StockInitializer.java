package com.stockexchange.exchange.util;


import com.stockexchange.exchange.domain.Stock;
import com.stockexchange.exchange.domain.StockTransaction;
import com.stockexchange.exchange.domain.StockType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Log4j2
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StockInitializer {


    private HashSet<Stock> stockSet;


    @PostConstruct
    private void init() {
        log.info("Preparing Stock details");
        Stock stockTea = new Stock("TEA", StockType.COMMON, 0.0, null, 100.0 );
        Stock stockPop = new Stock ("POP", StockType.COMMON, 8.0, null, 100.0);
        Stock stockAle = new Stock("ALE", StockType.COMMON, 23.0, null, 60.0);
        Stock stockGin = new Stock("GIN", StockType.PREFERRED, 8.0, 2.0, 100.0);
        Stock stockJoe = new Stock("JOE", StockType.COMMON, 130.0, null, 250.0);

        stockSet = new HashSet<>();
        stockSet.addAll(Arrays.asList(stockTea, stockPop, stockAle, stockGin, stockJoe));

    }

    public  Set<Stock> getStockSet() {
        return Collections.unmodifiableSet(stockSet);
    }

    public synchronized void addTransaction(StockTransaction stockTransaction) {
        Transaction transaction = Transaction.INSTANCE;
        transaction.addTransaction(stockTransaction);

        log.debug(transaction.getStockTransactionList().toString());
    }



}
