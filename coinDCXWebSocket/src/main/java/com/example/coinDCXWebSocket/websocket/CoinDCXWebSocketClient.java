package com.example.coinDCXWebSocket.websocket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.net.URI;
@Component
public class CoinDCXWebSocketClient extends WebSocketClient {
    private double currentPrice;
    public CoinDCXWebSocketClient(URI serverUri) {
        super(serverUri);
    }


    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("WebSocket opened");
        String subscriptionMessage = "{\"e\":\"subscribe\",\"d\":{\"pair\":\"BTCUSDT\"}}";
        send(subscriptionMessage);
    }

    @Override
    public void onMessage(String message) {
        // Log the raw message to inspect the format
        System.out.println("Received message: " + message);

        try {
            JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

            // Assuming the message contains a "d" (data) object with the relevant price info
            if (jsonObject.has("d")) {
                JsonObject dataObject = jsonObject.get("d").getAsJsonObject();

                // Check for the correct currency pair (e.g., BTC/USDT)
                if (dataObject.has("pair") && dataObject.get("pair").getAsString().equalsIgnoreCase("BTCUSDT")) {

                    // Extract the price field (may vary depending on API)
                    if (dataObject.has("last_price")) {
                        currentPrice = dataObject.get("last_price").getAsDouble();
                        System.out.println("Current Price: " + currentPrice);
                    } else {
                        System.out.println("Price field not found in message");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error parsing WebSocket message");
        }
    }


    public double getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}
