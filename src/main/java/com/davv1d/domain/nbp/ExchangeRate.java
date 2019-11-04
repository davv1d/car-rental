package com.davv1d.domain.nbp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExchangeRate {
    private String no;
    private String tradingDate;
    private List<Rates> rates;
    private String table;
    private String effectiveDate;
}
