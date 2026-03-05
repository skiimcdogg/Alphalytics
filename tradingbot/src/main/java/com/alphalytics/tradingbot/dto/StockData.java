package com.alphalytics.tradingbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockData {
    
    private LocalDate date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
}