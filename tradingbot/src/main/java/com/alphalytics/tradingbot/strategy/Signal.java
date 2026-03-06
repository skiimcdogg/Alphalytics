package com.alphalytics.tradingbot.strategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Signal {
    
    private LocalDate date;
    private SignalType type;
    private Double price;
    private String reason;  // DEBUG
}