package com.davv1d.mapper.nbp;

import com.davv1d.domain.nbp.ExchangeRate;
import com.davv1d.domain.nbp.Rates;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NbpMapper {

    public List<Rates> mapToRatesList(final List<ExchangeRate> exchangeRates) {
        return exchangeRates.stream()
                .flatMap(exchangeRate -> exchangeRate.getRates().stream())
                .collect(Collectors.toList());
    }
}
