package com.example.coinDCXWebSocket;

import com.example.coinDCXWebSocket.service.TradingService;
import com.example.coinDCXWebSocket.websocket.CoinDCXWebSocketClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.Scanner;

@SpringBootApplication
public class CoinDcxWebSocketApplication implements CommandLineRunner {
	@Autowired
	private TradingService tradingService;
	@Autowired
	private CoinDCXWebSocketClient coinDCXWebSocketClient;

	public static void main(String[] args) {
		SpringApplication.run(CoinDcxWebSocketApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to the CoinDCX Trading CLI");
		System.out.print("Please enter the trigger price: ");
		double triggerPrice = scanner.nextDouble();

		// Simulating market price data from WebSocket (this can be replaced by real-time data)
		double currentPrice = coinDCXWebSocketClient.getCurrentPrice();  // Example current price

		System.out.println("Trigger price set: " + triggerPrice);
		System.out.println("Current market price is: " + currentPrice);

		// Call the service to prepare the order payload
		tradingService.prepareOrderPayload(triggerPrice, currentPrice);

		scanner.close();
	}
}
