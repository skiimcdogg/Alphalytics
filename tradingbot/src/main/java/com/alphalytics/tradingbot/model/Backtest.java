package com.alphalytics.tradingbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "backtests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Backtest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String strategyName;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String strategyParams; // JSON stocké comme String
    
    @Column(nullable = false)
    private String symbol;
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    @Column(nullable = false)
    private LocalDate endDate;
    
    @Column(nullable = false)
    private Double initialCash;
    
    private Double finalEquity;
    private Double returnPct;
    private Double sharpeRatio;
    private Double maxDrawdown;
    private Integer totalTrades;
    private Double winRate;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "backtest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trade> trades = new ArrayList<>();
    
    @OneToMany(mappedBy = "backtest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquityCurve> equityCurve = new ArrayList<>();
}