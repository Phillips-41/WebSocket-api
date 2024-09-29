package com.example.coinDCXWebSocket.controller;

import com.example.coinDCXWebSocket.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trading")
public class TradingController {

    @Autowired
    private TradingService tradingService;

    @PostMapping("/trigger")
    public String setTriggerPrice(@RequestParam double triggerPrice, @RequestParam double currentPrice) {
        tradingService.prepareOrderPayload(triggerPrice, currentPrice);
        return "Order prepared based on trigger price: " + triggerPrice;
    }
}