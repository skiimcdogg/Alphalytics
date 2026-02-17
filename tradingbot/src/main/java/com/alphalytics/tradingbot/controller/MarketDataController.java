package com.alphalytics.tradingbot.controller;

import com.alphalytics.tradingbot.dto.StockData;
import com.alphalytics.tradingbot.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/market-data")
@RequiredArgsConstructor
public class MarketDataController {
    
    private final MarketDataService marketDataService;
    
    /**
     * Test endpoint pour récupérer des données historiques
     * GET /api/v1/market-data?symbol=AAPL&startDate=2023-01-01&endDate=2023-12-31
     */
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