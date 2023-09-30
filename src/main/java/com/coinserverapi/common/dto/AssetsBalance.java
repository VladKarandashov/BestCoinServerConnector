package com.coinserverapi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetsBalance {

    private String assetsSymbol;

    private BigDecimal assetsCount;

    private ChangeCost changeCost;
}