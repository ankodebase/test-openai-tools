package com.springai.test_openai_functions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class FxRateService {

    private final FxRateApiConfigProperties configProperties;
    private final RestClient restClient;

    public FxRateService(FxRateApiConfigProperties configProperties) {
        this.configProperties = configProperties;
        this.restClient = RestClient.create(configProperties.apiUrl());
    }


    @Tool(
            description = "Get foreign exchange rate between two currencies",
            name = "fx_rate",
            resultConverter = CustomToolCallResultConverter.class
    )
    public Response getRates(Request request) {
        log.info("Fetching FX rate from {} to {} at {}", request.from(), request.to(), new Date());
        Response response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("access_key", configProperties.apiKey())
                        .queryParam("base", request.from())
                        .queryParam("symbols", request.to())
                        .build())
                .retrieve()
                .body(Response.class);
        log.info("Fetched FX rate: {}", response);
        return response;
    }

    public record Request(String from, String to) {
    }

    public record Response(
            boolean success,
            long timestamp,
            String base,
            String date,
            Map<String, Double> rates
    ) {
    }

}
