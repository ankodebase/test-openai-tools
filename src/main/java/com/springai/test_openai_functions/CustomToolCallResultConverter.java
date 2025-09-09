package com.springai.test_openai_functions;

import org.springframework.ai.tool.execution.ToolCallResultConverter;

import java.lang.reflect.Type;
import java.time.Instant;

public class CustomToolCallResultConverter implements ToolCallResultConverter {
    @Override
    public String convert(Object result, Type returnType) {
        String response = "Current FX rate from " + ((FxRateService.Response) result).base() + " to " +
                ((FxRateService.Response) result).rates().keySet().stream().findFirst().orElse("unknown") + " is " +
                ((FxRateService.Response) result).rates().values().stream().findFirst().orElse(0.0) + " at "
                + Instant.ofEpochSecond(((FxRateService.Response) result).timestamp());
        return response;
    }
}
