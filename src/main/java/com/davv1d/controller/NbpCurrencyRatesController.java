package com.davv1d.controller;

import com.davv1d.domain.nbp.ExchangeRate;
import com.davv1d.externalApi.nbp.NbpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class NbpCurrencyRatesController {
    @Autowired
    private NbpClient nbpClient;

    @GetMapping("/rates")
    public List<ExchangeRate> getRates() {
        return nbpClient.getExchangeRates();
    }
}
