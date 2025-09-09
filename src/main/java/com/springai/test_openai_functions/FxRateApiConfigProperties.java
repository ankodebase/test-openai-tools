package com.springai.test_openai_functions;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "fxrate")
public record FxRateApiConfigProperties(String apiUrl, String apiKey) {
}
