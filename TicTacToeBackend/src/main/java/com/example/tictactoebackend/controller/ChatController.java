package com.example.tictactoebackend.controller;

import com.example.tictactoebackend.models.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/send")    // client sends to /app/send  app comes from websocket config
    @SendTo("/topic/messages")  // broadcast to subscribers
    public ChatMessage send(ChatMessage message) {
        System.out.println(message);
        return message;
    }
}

