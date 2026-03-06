package com.alphalytics.tradingbot.strategy.impl;

import java.util.ArrayList;
import java.util.List;

import com.alphalytics.tradingbot.dto.StockData;
import com.alphalytics.tradingbot.strategy.Signal;
import com.alphalytics.tradingbot.strategy.SignalType;
import com.alphalytics.tradingbot.strategy.Strategy;

public class SMAStrategy implements Strategy {
    private final int shortWindow;
    private final int longWindow;
    
    public SMAStrategy(int shortWindow, int longWindow) {
        if (shortWindow <= 0 || longWindow <= 0) {
            throw new IllegalArgumentException("Windows must be positive");
        }
        if (shortWindow >= longWindow) {
            throw new IllegalArgumentException("Short window must be smaller than long window");
        }
        this.shortWindow = shortWindow;
        this.longWindow = longWindow;
    }
    
    @Override
    public List<Signal> generateSignals(List<StockData> data) {
        List<Signal> signals = new ArrayList<>();

        if (data.size() < longWindow) {
            return signals;
        }

        for(int i = longWindow - 1; i < data.size(); i++) {
            double shortToday = calculateSMA(data, i, shortWindow);
            double longToday = calculateSMA(data, i, longWindow);

            SignalType signalType;

            if (i == longWindow - 1) {
                signalType = SignalType.HOLD;
            } else {
                double shortYesterday = calculateSMA(data, i - 1, shortWindow);
                double longYesterday = calculateSMA(data, i - 1, longWindow);

                if (shortYesterday <= longYesterday && shortToday > longToday) {
                    signalType = SignalType.BUY;
                } else if (shortYesterday >= longYesterday && shortToday < longToday) {
                    signalType = SignalType.SELL;
                } else {
                    signalType = SignalType.HOLD;
                };
            };

            StockData currentData = data.get(i);
            Signal signal = Signal.builder()
                .date(currentData.getDate())
                .type(signalType)
                .price(currentData.getClose())
                .reason(String.format("SMA(%d)=%.2f, SMA(%d)=%.2f", 
                            shortWindow, shortToday, longWindow, longToday))
                .build();
            
            signals.add(signal);
        }

        return signals;
    }
    
    private double calculateSMA(List<StockData> data, int currentIndex, int window) {
        int startIndex = currentIndex - window + 1;
        double sum = 0;
        for (int i = startIndex; i <= currentIndex; i++) {
            sum = sum + data.get(i).getClose();
        }
        return sum / window;
    }

    @Override
    public String getName() {
        return "SMA(" + shortWindow + "," + longWindow + ")";
    }
}