package com.alphalytics.tradingbot.strategy;

import com.alphalytics.tradingbot.dto.StockData;

import java.util.List;

public interface Strategy {
    
    /**
     * generate trading signals from historical data
     * 
     * @param data list of historical data (sorted by date)
     * @return list of signals (one per day)
     */
    List<Signal> generateSignals(List<StockData> data);
    
    /**
     * return the name of the strategy
     */
    String getName();
}