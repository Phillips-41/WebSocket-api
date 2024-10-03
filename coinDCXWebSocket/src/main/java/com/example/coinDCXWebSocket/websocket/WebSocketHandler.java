package com.example.coinDCXWebSocket.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

public class WebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    private static final String COINDCX_WEBSOCKET_URL = "wss://stream.coindcx.com";
    private Socket socket;
    private final double triggerPrice;
    private final String currencyPair;
    private final OrderManager orderManager;

    public WebSocketHandler(double triggerPrice, String currencyPair) {
        this.triggerPrice = triggerPrice;
        this.currencyPair = currencyPair;
        this.orderManager = new OrderManager(triggerPrice, currencyPair);
    }

    public void connect() {
        try {
            IO.Options options = IO.Options.builder()
                    .setTransports(new String[]{"websocket"})  // Use WebSocket transport only
                    .build();

            logger.info("Attempting to connect to WebSocket at: " + COINDCX_WEBSOCKET_URL);

            socket = IO.socket(COINDCX_WEBSOCKET_URL, options);

            socket.on(Socket.EVENT_CONNECT, args -> {
                logger.info("Successfully connected to WebSocket.");

                // Subscribe to BTCUSDT order book updates
                logger.info("Subscribing to depth channel 'BTCUSDT@orderbook@20'.");
                socket.emit("join", "{\"channelName\":\"BTCUSDT@orderbook@20\"}");
            });

            socket.on("join", args -> {
                logger.info("Successfully joined the channel: " + args[0]);
            });

            socket.on("depth-update", this::handleDepthUpdate);

            socket.on("disconnect", args -> {
                logger.warn("WebSocket disconnected.");
            });

            socket.on("error", args -> {
                logger.error("WebSocket error occurred: " + args[0]);
            });

            socket.on("connect_timeout", args -> {
                logger.error("WebSocket connection timed out.");
            });

            socket.on("connect_error", args -> {
                logger.error("WebSocket connection error: " + args[0]);
            });

            socket.connect();
        } catch (URISyntaxException e) {
            logger.error("Error connecting to WebSocket", e);
        }
    }

    private void handleDepthUpdate(Object... args) {
        try {
            String message = args[0].toString();
            logger.info("Received depth update: " + message);  // Log the received message

            // Parse order book using OrderBookParser
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(message);
            JsonNode asksNode = rootNode.path("asks");
            JsonNode bidsNode = rootNode.path("bids");

            // Extract best ask and bid prices
            Double bestAsk = OrderBookParser.getBestAsk(asksNode);
            Double bestBid = OrderBookParser.getBestBid(bidsNode);

            logger.info("Best Ask: " + bestAsk + ", Best Bid: " + bestBid);

            // Check trigger conditions for buy/sell order
            orderManager.processOrder(bestAsk, bestBid);
        } catch (Exception e) {
            logger.error("Error processing depth update message: ", e);
        }
    }

    public void cancelOrder() {
        orderManager.simulateCancelOrder();
    }
}
