package com.example.coinDCXWebSocket.model;

import lombok.Data;

@Data
public class OrderPayload {
    private String type;
    private String pair;
    private double price;
    private double quantity;

    public OrderPayload(String type, String pair, double price, double quantity) {
        this.type = type;
        this.pair = pair;
        this.price = price;
        this.quantity = quantity;
    }
}
