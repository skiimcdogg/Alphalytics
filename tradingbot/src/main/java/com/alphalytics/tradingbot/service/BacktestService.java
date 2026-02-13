package com.alphalytics.tradingbot.service;

import com.alphalytics.tradingbot.repository.BacktestRepository;
import com.alphalytics.tradingbot.dto.BacktestRequest;
import com.alphalytics.tradingbot.dto.BacktestResponse;
import com.alphalytics.tradingbot.model.Backtest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class BacktestService {

    private final BacktestRepository backtestRepository;

    // Spring inject automatically the repository
    public BacktestService(BacktestRepository backtestRepository) {
        this.backtestRepository = backtestRepository;
    }

    private BacktestResponse convertToResponse(Backtest backtest) {
        BacktestResponse response = new BacktestResponse();
        response.setId(backtest.getId());
        response.setSymbol(backtest.getSymbol());
        response.setStrategyName(backtest.getStrategyName());
        response.setStartDate(backtest.getStartDate());
        response.setEndDate(backtest.getEndDate());
        response.setInitialCash(backtest.getInitialCash());
        response.setFinalEquity(backtest.getFinalEquity());
        response.setReturnPct(backtest.getReturnPct());
        response.setSharpeRatio(backtest.getSharpeRatio());
        response.setMaxDrawdown(backtest.getMaxDrawdown());
        response.setTotalTrades(backtest.getTotalTrades());
        response.setWinRate(backtest.getWinRate());
        response.setCreatedAt(backtest.getCreatedAt());
        return response;
    }

    public List<BacktestResponse> getAllBacktests() {
        return backtestRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public BacktestResponse getBacktestById(String id) {
        Backtest backtest = backtestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Backtest not found with id: " + id));

        return convertToResponse(backtest);
    }
        
    public BacktestResponse createBacktest(BacktestRequest request) {
        Backtest backtest = new Backtest();
        backtest.setSymbol(request.getSymbol());
        backtest.setStrategyName(request.getStrategyName());
        backtest.setStartDate(request.getStartDate());
        backtest.setEndDate(request.getEndDate());
        backtest.setInitialCash(request.getInitialCash());

        if (request.getStrategyParams() != null) {
            backtest.setStrategyParams(request.getStrategyParams());
        } else {
            backtest.setStrategyParams("{}");
        }

        backtest.setFinalEquity(request.getInitialCash());
        backtest.setReturnPct(0.0);
        backtest.setTotalTrades(0);

        Backtest saved = backtestRepository.save(backtest);
        return convertToResponse(saved);
    }

    public void deleteBacktest(String id) {
        if (!backtestRepository.existsById(id)) {
            throw new RuntimeException("Backtest not found with id: " + id);
        }
        backtestRepository.deleteById(id);
    }
}