package com.coinserverapi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeCost {

    private BigDecimal spentUsd;

    private BigDecimal costUsd;

    private BigDecimal percent;

    public ChangeCost(BigDecimal spentUsd, BigDecimal costUsd) {
        this(
                spentUsd,
                costUsd,
                costUsd
                        .divide(spentUsd, 32, RoundingMode.FLOOR)
                        .subtract(BigDecimal.ONE)
                        .multiply(BigDecimal.TEN.multiply(BigDecimal.TEN))
        );
    }
}