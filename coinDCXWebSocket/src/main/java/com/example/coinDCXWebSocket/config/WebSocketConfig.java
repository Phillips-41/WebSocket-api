package com.example.coinDCXWebSocket.config;

import com.example.coinDCXWebSocket.websocket.CoinDCXWebSocketClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class WebSocketConfig {
    @Bean
    public CoinDCXWebSocketClient coinDCXWebSocketClient() throws Exception {
        URI url = new URI("wss://stream.coindcx.com"); // WebSocket URL for CoinDCX
        CoinDCXWebSocketClient client = new CoinDCXWebSocketClient(url);
        client.connect();  // Connect to the WebSocket
        return client;
    }
}