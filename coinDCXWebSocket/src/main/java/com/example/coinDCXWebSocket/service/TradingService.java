package com.example.coinDCXWebSocket.service;

import com.example.coinDCXWebSocket.model.OrderPayload;
import org.springframework.stereotype.Service;

@Service
public class TradingService {

    public void prepareOrderPayload(double triggerPrice, double currentPrice) {
        OrderPayload payload;

        if (currentPrice <= triggerPrice) {
            // Prepare a Buy Order Payload
            payload = new OrderPayload("buy", "BTCUSDT", currentPrice, 1.0);  // Example: Buy 1 BTC at current price
            System.out.println("Buy Order Prepared: " + payload);
        } else if (currentPrice >= triggerPrice) {
            // Prepare a Sell Order Payload
            payload = new OrderPayload("sell", "BTCUSDT", currentPrice, 1.0);  // Example: Sell 1 BTC at current price
            System.out.println("Sell Order Prepared: " + payload);
        }
    }
}
