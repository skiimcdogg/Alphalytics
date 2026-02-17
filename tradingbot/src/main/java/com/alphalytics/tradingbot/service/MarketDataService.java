package com.alphalytics.tradingbot.service;

import com.alphalytics.tradingbot.dto.StockData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MarketDataService {
    /**
     * Gets historical data of a stock
     * 
     * @param symbol Stock symbol (ex: "AAPL")
     * @param startDate Start date
     * @param endDate End date
     * @return List of StockData (daily prices)
     */
    public List<StockData> getHistoricalData(String symbol, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching historical data for {} from {} to {}", symbol, startDate, endDate);
        
        try {
            // Get the Stock object from Yahoo Finance
            Stock stock = YahooFinance.get(symbol);
            
            if (stock == null) {
                throw new RuntimeException("Stock not found: " + symbol);
            }
            
            // Convert LocalDate to Calendar (required by Yahoo API)
            Calendar startDateCalendar = dateToCalendar(startDate);
            Calendar endDateCalendar = dateToCalendar(endDate);
            
            // Get the history (daily data)
            List<HistoricalQuote> history = stock.getHistory(startDateCalendar, endDateCalendar, Interval.DAILY);
            
            if (history == null || history.isEmpty()) {
                log.warn("No historical data found for {}", symbol);
                return Collections.emptyList();
            }
            
            // Convert HistoricalQuote to StockData
            List<StockData> stockDataList = history.stream()
                    .map(this::convertToStockData)
                    .filter(Objects::nonNull)  // Remove nulls
                    .sorted(Comparator.comparing(StockData::getDate))  // Sort by date
                    .collect(Collectors.toList());
            
            log.info("Fetched {} data points for {}", stockDataList.size(), symbol);
            
            return stockDataList;
            
        } catch (IOException e) {
            log.error("Error fetching data for {}: {}", symbol, e.getMessage());
            throw new RuntimeException("Failed to fetch market data for " + symbol, e);
        }
    }
    
    /**
     * Converts HistoricalQuote (Yahoo Finance) → StockData (our DTO)
     */
    private StockData convertToStockData(HistoricalQuote quote) {
        if (quote == null || quote.getDate() == null) {
            return null;
        }
        
        return StockData.builder()
                .date(calendarToLocalDate(quote.getDate()))
                .open(quote.getOpen() != null ? quote.getOpen().doubleValue() : null)
                .high(quote.getHigh() != null ? quote.getHigh().doubleValue() : null)
                .low(quote.getLow() != null ? quote.getLow().doubleValue() : null)
                .close(quote.getClose() != null ? quote.getClose().doubleValue() : null)
                .volume(quote.getVolume())
                .build();
    }
    
    /**
     * Converts LocalDate → Calendar (required by Yahoo API)
     */
    private Calendar dateToCalendar(LocalDate date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return calendar;
    }
    
    /**
     * Converts Calendar → LocalDate
     */
    private LocalDate calendarToLocalDate(Calendar calendar) {
        return calendar.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}