package com.example.coinDCXWebSocket.websocket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderManager {
    private static final Logger logger = LoggerFactory.getLogger(OrderManager.class);
    private final double triggerPrice;
    private final String currencyPair;
    private boolean orderExecuted = false;

    public OrderManager(double triggerPrice, String currencyPair) {
        this.triggerPrice = triggerPrice;
        this.currencyPair = currencyPair;
    }

    public void processOrder(Double bestAsk, Double bestBid) {
        if (!orderExecuted && bestAsk != null && bestBid != null) {
            if (triggerPrice >= bestAsk) {
                prepareBuyOrder(bestAsk);
            } else if (triggerPrice <= bestBid) {
                prepareSellOrder(bestBid);
            }
        }
    }

    private void prepareBuyOrder(double bestAsk) {
        logger.info("Triggering BUY order. Best ask: " + bestAsk + ", Trigger price: " + triggerPrice);
        String buyOrderPayload = "{ \"orderType\": \"BUY\", \"price\": " + bestAsk + ", \"triggerPrice\": " + triggerPrice + ", \"currencyPair\": \"" + currencyPair + "\" }";
        logger.info("Prepared BUY order payload: " + buyOrderPayload);
        orderExecuted = true;  // Mark the order as executed
    }

    private void prepareSellOrder(double bestBid) {
        logger.info("Triggering SELL order. Best bid: " + bestBid + ", Trigger price: " + triggerPrice);
        String sellOrderPayload = "{ \"orderType\": \"SELL\", \"price\": " + bestBid + ", \"triggerPrice\": " + triggerPrice + ", \"currencyPair\": \"" + currencyPair + "\" }";
        logger.info("Prepared SELL order payload: " + sellOrderPayload);
        orderExecuted = true;  // Mark the order as executed
    }

    public void simulateCancelOrder() {
        logger.info("Simulating order cancellation...");
        String cancelOrderPayload = "{ \"action\": \"CANCEL_ORDER\", \"currencyPair\": \"" + currencyPair + "\" }";
        logger.info("Prepared order cancellation payload: " + cancelOrderPayload);
    }
}

