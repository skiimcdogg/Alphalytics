package com.alphalytics.tradingbot.dto.alphavantage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class AlphaVantageDailyResponse {

    @JsonProperty("Meta Data")
    private Map<String, String> metaData;

    @JsonProperty("Time Series (Daily)")
    private Map<String, DailyQuote> timeSeries;
}