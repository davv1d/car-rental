package com.davv1d.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class NbpApiConfig {
    @Value("${nbp.api.endpoint.prod}")
    private String nbpApiEndpoint;
}
