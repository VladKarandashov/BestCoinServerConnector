package com.coinserverapi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceInfo {

    private BigDecimal usdMoney;

    private List<AssetsBalance> assets;

    private ChangeCost changeCost;
}