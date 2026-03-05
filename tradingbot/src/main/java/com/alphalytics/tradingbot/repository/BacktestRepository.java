package com.alphalytics.tradingbot.repository;

import com.alphalytics.tradingbot.model.Backtest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BacktestRepository extends JpaRepository<Backtest, String> {
    
    // Spring Data JPA generates the following methods automatically:
    // - save(backtest)
    // - findById(id)
    // - findAll()
    // - deleteById(id)
    
    // Custom methods (Spring generates the implementation automatically)
    List<Backtest> findBySymbol(String symbol);
    List<Backtest> findByStrategyName(String strategyName);
}