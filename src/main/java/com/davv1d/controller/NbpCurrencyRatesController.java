package com.davv1d.controller;

import com.davv1d.domain.nbp.Rates;
import com.davv1d.externalApi.nbp.NbpClient;
import com.davv1d.mapper.nbp.NbpMapper;
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

    @Autowired
    private NbpMapper nbpMapper;

    @GetMapping("/rates")
    public List<Rates> getRates() {
        return nbpMapper.mapToRatesList(nbpClient.getExchangeRates());
    }
}
