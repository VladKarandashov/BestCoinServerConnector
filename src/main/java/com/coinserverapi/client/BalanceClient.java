package com.coinserverapi.client;

import com.coinserverapi.common.dto.AssetsBalance;
import com.coinserverapi.common.dto.BalanceInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AccessLevel;
import lombok.Setter;
import okhttp3.Request;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class BalanceClient {

    private final String serverUrl;

    @Setter(AccessLevel.PACKAGE)
    private String token;

    protected BalanceClient(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    private static final String BALANCE_PREFIX = "/api/v1/balance";

    public Optional<BigDecimal> getUsdBalance() {

        Request request = new Request.Builder()
                .url(serverUrl + BALANCE_PREFIX)
                .addHeader("token", token)
                .build();

        return CoinServerClient.processRequest(request, new TypeReference<>() {
        });
    }

    public Optional<List<AssetsBalance>> getAssetsBalance() {

        Request request = new Request.Builder()
                .url(serverUrl + BALANCE_PREFIX + "/assets")
                .addHeader("token", token)
                .build();

        return CoinServerClient.processRequest(request, new TypeReference<>() {
        });
    }

    public Optional<AssetsBalance> getAssetsBalance(String symbol) {

        Request request = new Request.Builder()
                .url(serverUrl + BALANCE_PREFIX + "/assets/" + symbol)
                .addHeader("token", token)
                .build();

        return CoinServerClient.processRequest(request, new TypeReference<>() {
        });
    }

    public Optional<BalanceInfo> getBalance() {

        Request request = new Request.Builder()
                .url(serverUrl + BALANCE_PREFIX + "/info")
                .addHeader("token", token)
                .build();
        return CoinServerClient.processRequest(request, new TypeReference<>() {
        });
    }
}
