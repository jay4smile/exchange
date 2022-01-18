package com.stockexchange.exchange.controller;


import com.stockexchange.exchange.domain.Response;
import com.stockexchange.exchange.domain.StockTransaction;
import com.stockexchange.exchange.exception.InvalidDataFoundException;
import com.stockexchange.exchange.service.StockService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequestMapping(path = "/api/exchange")
@RestController
public class ExchangeController {


    @Autowired
    private StockService stockService;

    @GetMapping(path = "/{stockname}/{price}/dividend")
    public Response calculateDividend(@PathVariable(name = "stockname") String stockName,
                                      @PathVariable("price") Double price) {
        log.info("calculate dividend");
        return stockService.calculateDividend(stockName, price);
    }

    @GetMapping(path = "/{stockname}/{price}/pneratio")
    public Response calculatePnERatio(@PathVariable(name = "stockname") String stockName,
                                      @PathVariable("price") Double price) {
        log.info("calculate p&e ratio");
        return stockService.calculatePnERation(stockName, price);
    }

    @PostMapping(path = "/{stockname}")
    public Response saveStockTransaction(@PathVariable("stockname") String stockName, @RequestBody StockTransaction stockTransaction) {
        log.info("Transaction {}", stockTransaction.toString());
        validateRequest(stockTransaction, stockName);
        stockTransaction.setStockName(stockName.toUpperCase());
        return stockService.submitTransaction(stockTransaction);
    }

    private void validateRequest(StockTransaction stockTransaction, String stockname) {
        if (!StringUtils.hasText(stockname) || (null == stockTransaction.getQuantity() || null == stockTransaction.getQuantity())) {
            throw new InvalidDataFoundException("Invalid request");
        }
    }

    @GetMapping(path = "/{stockname}/volume")
    public Response calculateVolumeWeightedStock(@PathVariable(name = "stockname") String stockName) {
        log.info("calculate volume");
        return stockService.calculateVolumeWeight(stockName);
    }

    @GetMapping(path = "/")
    public Response calculateGeomatricMean() {
        log.info("calculate volume");
        return stockService.calculateGeomatricMean();
    }
}
