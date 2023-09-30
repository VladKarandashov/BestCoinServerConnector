package com.coinserverapi.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBuyOrderRequest {

    private String assetsSymbol;

    private BigDecimal usdMoney;
}