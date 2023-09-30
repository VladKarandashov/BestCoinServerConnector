package com.coinserverapi.client;

import com.coinserverapi.common.dto.TickerPrice;
import com.coinserverapi.common.dto.TickerStatistics;
import com.coinserverapi.common.exception.CoinServerException;
import com.coinserverapi.common.exception.ExceptionBasis;
import com.coinserverapi.common.response.CandlestickResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PricesClient {

    private final String serverUrl;

    protected PricesClient(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String PRICES_PREFIX = "/api/v1/coins";

    public Optional<List<TickerPrice>> getAllPrices() {
        Request request = new Request.Builder()
                .url(serverUrl + PRICES_PREFIX + "/prices")
                .build();

        return CoinServerClient.callOpt(request).map(body -> {
            try {
                return mapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                throw new CoinServerException(ExceptionBasis.INTERNAL_SERVER_ERROR, e);
            }
        });
    }

    public Optional<TickerStatistics> getStatisticsBySymbol(String symbol) {
        Request request = new Request.Builder()
                .url(serverUrl + PRICES_PREFIX + "/statistics/" + symbol)
                .build();

        return CoinServerClient.callOpt(request).map(body -> {
            try {
                return mapper.readValue(body, TickerStatistics.class);
            } catch (JsonProcessingException e) {
                throw new CoinServerException(ExceptionBasis.INTERNAL_SERVER_ERROR, e);
            }
        });
    }

    public Optional<List<CandlestickResponse>> getCandlestickBarsBySymbol(String symbol) {
        return getCandlestickBarsBySymbol(symbol, null);
    }

    public Optional<List<CandlestickResponse>> getCandlestickBarsBySymbol(String symbol, String interval) {
        var httpBuilder = Objects.requireNonNull(
                HttpUrl.parse(serverUrl + PRICES_PREFIX + "/prices/" + symbol)).newBuilder();
        if (interval != null) {
            httpBuilder.addQueryParameter("interval", interval);
        }

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();

        return CoinServerClient.callOpt(request).map(body -> {
            try {
                return mapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                throw new CoinServerException(ExceptionBasis.INTERNAL_SERVER_ERROR, e);
            }
        });
    }
}
