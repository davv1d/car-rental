package com.davv1d.externalApi.nbp;

import com.davv1d.config.NbpApiConfig;
import com.davv1d.domain.nbp.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class NbpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(NbpClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NbpApiConfig nbpApiConfig;

    public List<ExchangeRate> getExchangeRates() {
        URI uri = getNbpUri();
        try {
            ExchangeRate[] exchangeRates = restTemplate.getForObject(uri, ExchangeRate[].class);
            return Arrays.asList(Optional.ofNullable(exchangeRates).orElse(new ExchangeRate[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    private URI getNbpUri() {
        return UriComponentsBuilder.fromHttpUrl(nbpApiConfig.getNbpApiEndpoint())
                .queryParam("format", "json").build().encode().toUri();
    }
}
