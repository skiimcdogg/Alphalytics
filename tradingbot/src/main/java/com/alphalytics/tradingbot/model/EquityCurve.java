package com.alphalytics.tradingbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "equity_curve")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquityCurve {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "backtest_id", nullable = false)
    private Backtest backtest;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    private Double equity;
}