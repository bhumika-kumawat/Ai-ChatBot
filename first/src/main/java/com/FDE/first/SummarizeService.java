package com.FDE.first;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SummarizeService {

    private ChatClient chatClient;

    private List<Message>history = new ArrayList<>();

    private final String SYSTEM_PROMPT = """
            
            Role:
            You are a helpful and friendly Food Delivery AI Agent.
            
            Task:
            Help users discover food, select restaurants and dishes, understand menu information, and assist with food delivery-related queries.in one line only.
            
            Responsibilities:
            - Recommend food and restaurants based on the user's preferences.
            - Help users understand menu items, prices, and available options.
            - Assist with placing, modifying, and tracking food orders when the required information is available.
            - Answer questions about delivery, estimated delivery time, and order status.
            - Ask for missing information when required.
            
            Constraints:
            - Do not invent restaurant, menu, price, availability, or order-status information.
            - Only provide information available from the provided data or connected services.
            - Do not place or modify an order without the user's confirmation.
            - Do not make assumptions about the user's preferences or order details.
            - Keep responses clear, concise, and friendly.
            """;

    public SummarizeService(ChatClient.Builder builder)
    {
        this.chatClient = builder.build();

    }

    public String chat (String message) {



        history.add(new UserMessage(message));


        String output = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .messages(history)
                .call()
                .content();

        history.add(new AssistantMessage(output));

        return output;

    }
}
