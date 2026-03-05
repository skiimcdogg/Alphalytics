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
}