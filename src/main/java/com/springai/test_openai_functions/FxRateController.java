package com.springai.test_openai_functions;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class FxRateController {

    private final ChatClient chatClient;
    private final FxRateApiConfigProperties fxRateApiConfigProperties;

    public FxRateController(ChatClient.Builder chatClientBuilder, FxRateApiConfigProperties configProperties) {
        this.chatClient = chatClientBuilder.build();
        fxRateApiConfigProperties = configProperties;
    }

    @GetMapping("/fx/rate")
    public String getFxRate(@RequestParam String from, @RequestParam String to) {
        String systemMessageText = """
                You are a financial expert. Provide accurate and up-to-date foreign exchange rates.
                Do not make up rates if you don't know them. Also do not search Web
                """;
        SystemMessage systemMessage = new SystemMessage(systemMessageText);
        UserMessage userMessage = new UserMessage(String.format("What is the current FX rate from %s to %s?", from, to));
        UserMessage m = UserMessage.builder().text("").media(new Media(MimeTypeUtils.IMAGE_PNG, new ClassPathResource(""))).build();

        Prompt prompt = Prompt.builder().messages(List.of(systemMessage, userMessage)).build();
        return chatClient.prompt(prompt).tools(new FxRateService(fxRateApiConfigProperties)).call().content();
    }

}
