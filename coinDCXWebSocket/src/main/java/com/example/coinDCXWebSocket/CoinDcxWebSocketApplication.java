package com.example.coinDCXWebSocket;


import com.example.coinDCXWebSocket.websocket.WebSocketHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;


@SpringBootApplication
public class CoinDcxWebSocketApplication implements CommandLineRunner {


	private double triggerPrice;
	private final String currencyPair = "BTCUSDT";  // Default currency pair

	public static void main(String[] args) {
		SpringApplication.run(CoinDcxWebSocketApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the trigger price for " + currencyPair + ": ");
		triggerPrice = scanner.nextDouble();
		System.out.println("Monitoring market data for " + currencyPair + " with trigger price: " + triggerPrice);

		// Initialize WebSocketHandler with the currency pair and trigger price
		WebSocketHandler webSocketHandler = new WebSocketHandler(triggerPrice, currencyPair);
		webSocketHandler.connect();

		// Manual cancellation logic via command-line
		System.out.println("Enter 'cancel' to cancel the order at any time.");
		while (true) {
			String input = scanner.nextLine();
			if (input.equalsIgnoreCase("cancel")) {
				webSocketHandler.cancelOrder();
				break;
			}
		}
	}

}
