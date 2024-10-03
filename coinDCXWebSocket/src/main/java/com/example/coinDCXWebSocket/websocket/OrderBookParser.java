package com.example.coinDCXWebSocket.websocket;


import com.fasterxml.jackson.databind.JsonNode;

import java.util.Comparator;
import java.util.TreeMap;

public class OrderBookParser {

    public static Double getBestAsk(JsonNode asksNode) {
        TreeMap<Double, Double> asks = parseOrderBook(asksNode);
        return asks.isEmpty() ? null : asks.firstKey();
    }

    public static Double getBestBid(JsonNode bidsNode) {
        TreeMap<Double, Double> bids = parseOrderBook(bidsNode);
        return bids.isEmpty() ? null : bids.lastKey();
    }

    private static TreeMap<Double, Double> parseOrderBook(JsonNode node) {
        TreeMap<Double, Double> orderBook = new TreeMap<>(Comparator.naturalOrder());
        if (node != null) {
            node.fields().forEachRemaining(entry -> {
                double price = Double.parseDouble(entry.getKey());
                double quantity = entry.getValue().asDouble();
                if (quantity > 0) {  // Only consider orders with quantity > 0
                    orderBook.put(price, quantity);
                }
            });
        }
        return orderBook;
    }
}

