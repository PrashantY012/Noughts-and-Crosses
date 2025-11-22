package com.example.tictactoebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@SpringBootApplication
public class TicTacToeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicTacToeBackendApplication.class, args);
	}

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		System.out.println("Client connected: " + event.getUser());
		String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
		System.out.println("Client connected with session: " + sessionId);
	}
}
