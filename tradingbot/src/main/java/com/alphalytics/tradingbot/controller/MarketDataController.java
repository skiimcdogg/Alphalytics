package com.alphalytics.tradingbot.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alphalytics.tradingbot.dto.StockData;
import com.alphalytics.tradingbot.service.MarketDataService;
import com.alphalytics.tradingbot.strategy.Signal;
import com.alphalytics.tradingbot.strategy.impl.SMAStrategy;
import com.alphalytics.tradingbot.strategy.Strategy;

@RestController
@RequestMapping("/api/v1/market-data")
public class MarketDataController {
    
    private final MarketDataService marketDataService;
    
    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }
    
    @GetMapping
    public ResponseEntity<List<StockData>> getMarketData(
            @RequestParam String symbol,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        
        List<StockData> data = marketDataService.getHistoricalData(symbol, start, end);
        
        return ResponseEntity.ok(data);
    }

    @GetMapping("/test-sma")
    public ResponseEntity<List<Signal>> testSMAStrategy(
        @RequestParam(defaultValue = "AAPL") String symbol,
        @RequestParam(defaultValue = "10") int shortWindow,
        @RequestParam(defaultValue = "20") int longWindow
    ) {
        LocalDate endDate = LocalDate.now();
        LocalDate starDate = endDate.minusMonths(2);

        List<StockData> data = marketDataService.getHistoricalData(symbol, starDate, endDate);
        Strategy strategy = new SMAStrategy(shortWindow, longWindow);
        List<Signal> signals = strategy.generateSignals(data);

        return ResponseEntity.ok(signals);
    }
}