package com.alphalytics.tradingbot.controller;

import com.alphalytics.tradingbot.service.BacktestService;
import com.alphalytics.tradingbot.dto.BacktestRequest;
import com.alphalytics.tradingbot.dto.BacktestResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/backtests")
public class BacktestController {
    
    private final BacktestService backtestService;
    
    public BacktestController(BacktestService backtestService) {
        this.backtestService = backtestService;
    }

    @GetMapping
    public ResponseEntity<List<BacktestResponse>> getAllBacktests() {
        List<BacktestResponse> backtests = backtestService.getAllBacktests();
        return ResponseEntity.ok(backtests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BacktestResponse> getBacktestById(@PathVariable String id) {
        BacktestResponse backtest = backtestService.getBacktestById(id);
        return ResponseEntity.ok(backtest);
    }

    @PostMapping
    public ResponseEntity<BacktestResponse> createBacktest(
            @Valid @RequestBody BacktestRequest request) {
        
        BacktestResponse response = backtestService.createBacktest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBacktest(@PathVariable String id) {
        backtestService.deleteBacktest(id);
        return ResponseEntity.noContent().build();
    }
}