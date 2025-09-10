package com.springai.test_openai_functions;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImageController {

    private final ChatClient chatClient;

    @Value("classpath:/images/test.jpg")
    private Resource imageResource;

    @Value("classpath:/images/code-image.png")
    private Resource codeImageResource;


    public ImageController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/images/describe")
    public String describe() {
        String message = "Can you explain what you see in the following image?";
        UserMessage userMessage = UserMessage.builder().text(message).media(new Media(MimeTypeUtils.IMAGE_JPEG, imageResource)).build();
        Prompt prompt = Prompt.builder().messages(List.of(userMessage)).build();
        return chatClient.prompt(prompt).call().content();
    }

    @GetMapping("/images/code-image/describe")
    public String describeCodeImage() {
        String message = "Can you explain what you see in the following image?";
        UserMessage userMessage = UserMessage.builder().text(message).media(new Media(MimeTypeUtils.IMAGE_PNG, codeImageResource)).build();
        Prompt prompt = Prompt.builder().messages(List.of(userMessage)).build();
        return chatClient.prompt(prompt).call().content();
    }

    @GetMapping("/images/code-image/generate-code")
    public String generateCodeFromImage() {
        String message = "Can you generate code from what you see in the following image?";
        UserMessage userMessage = UserMessage.builder().text(message).media(new Media(MimeTypeUtils.IMAGE_PNG, codeImageResource)).build();
        Prompt prompt = Prompt.builder().messages(List.of(userMessage)).build();
        return chatClient.prompt(prompt).call().content();
    }

}
