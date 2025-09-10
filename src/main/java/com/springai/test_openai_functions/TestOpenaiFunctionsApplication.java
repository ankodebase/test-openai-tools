package com.springai.test_openai_functions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(FxRateApiConfigProperties.class)
@SpringBootApplication
public class TestOpenaiFunctionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestOpenaiFunctionsApplication.class, args);
    }

}
