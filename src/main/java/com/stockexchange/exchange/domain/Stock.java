package com.stockexchange.exchange.domain;

import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@AllArgsConstructor
public class Stock {

    @EqualsAndHashCode.Include
    private String stock;

    private StockType type;
    private Double lastDividend;
    private Double fixedDividend;
    private Double parValue;

}
