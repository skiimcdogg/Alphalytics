package com.alphalytics.tradingbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BacktestResponse {
    
    private String id;
    private String symbol;
    private String strategyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double initialCash;
    private Double finalEquity;
    private Double returnPct;
    private Double sharpeRatio;
    private Double maxDrawdown;
    private Integer totalTrades;
    private Double winRate;
    private LocalDateTime createdAt;
}