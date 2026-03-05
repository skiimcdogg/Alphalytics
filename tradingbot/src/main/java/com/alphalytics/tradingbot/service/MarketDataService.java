package com.alphalytics.tradingbot.service;

import com.alphalytics.tradingbot.config.AlphaVantageConfig;
import com.alphalytics.tradingbot.dto.StockData;
import com.alphalytics.tradingbot.dto.alphavantage.AlphaVantageDailyResponse;
import com.alphalytics.tradingbot.dto.alphavantage.DailyQuote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MarketDataService {

    private final WebClient webClient;
    private final AlphaVantageConfig config;

    public MarketDataService(WebClient webClient, AlphaVantageConfig config) {
        this.webClient = webClient;
        this.config = config;
    }

public List<StockData> getHistoricalData(String symbol, LocalDate startDate, LocalDate endDate) {
    log.info("Fetching historical data for {} from {} to {}", symbol, startDate, endDate);

    AlphaVantageDailyResponse response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/query")
                    .queryParam("function", "TIME_SERIES_DAILY")
                    .queryParam("symbol", symbol)
                    .queryParam("outputsize", "compact")
                    .queryParam("apikey", config.getApiKey())
                    .build())
            .retrieve()
            .bodyToMono(AlphaVantageDailyResponse.class)
            .block();

    if (response == null || response.getTimeSeries() == null) {
        log.warn("No data received for {}", symbol);
        return Collections.emptyList();
    }

    log.info("Received {} total data points from Alpha Vantage", response.getTimeSeries().size());

    List<StockData> stockDataList = response.getTimeSeries().entrySet().stream()
            .map(entry -> convertToStockData(entry.getKey(), entry.getValue()))
            .filter(data -> !data.getDate().isBefore(startDate) && !data.getDate().isAfter(endDate))
            .sorted(java.util.Comparator.comparing(StockData::getDate))
            .collect(Collectors.toList());

    log.info("Found {} data points for {} between {} and {}", 
            stockDataList.size(), symbol, startDate, endDate);

    return stockDataList;
}

    private StockData convertToStockData(String dateString, DailyQuote quote) {
        return StockData.builder()
                .date(LocalDate.parse(dateString))
                .open(Double.parseDouble(quote.getOpen()))
                .high(Double.parseDouble(quote.getHigh()))
                .low(Double.parseDouble(quote.getLow()))
                .close(Double.parseDouble(quote.getClose()))
                .volume(Long.parseLong(quote.getVolume()))
                .build();
    }
}