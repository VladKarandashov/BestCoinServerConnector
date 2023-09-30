package com.coinserverapi.client;

import com.coinserverapi.common.response.OrderResponse;
import com.coinserverapi.common.request.CreateBuyOrderRequest;
import com.coinserverapi.common.request.CreateSellAllOrderRequest;
import com.coinserverapi.common.request.CreateSellOrderRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AccessLevel;
import lombok.Setter;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.List;
import java.util.Optional;

public class OrdersClient {

    private final String serverUrl;

    @Setter(AccessLevel.PACKAGE)
    private String token;

    protected OrdersClient(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    private static final String ORDERS_PREFIX = "/api/v1/orders";

    public Optional<List<OrderResponse>> getAllOrders() {

        Request request = new Request.Builder()
                .url(serverUrl + ORDERS_PREFIX)
                .addHeader("token", token)
                .build();

        return CoinServerClient.processRequest(request, new TypeReference<>() {
        });
    }

    public Optional<List<OrderResponse>> getOrdersBySymbol(String symbol) {

        Request request = new Request.Builder()
                .url(serverUrl + ORDERS_PREFIX + "/" + symbol)
                .addHeader("token", token)
                .build();

        return CoinServerClient.processRequest(request, new TypeReference<>() {
        });
    }

    public Boolean createBuyOrder(CreateBuyOrderRequest createBuyOrderRequest) {

        Request request = new Request.Builder()
                .url(serverUrl + ORDERS_PREFIX + "/create")
                .post(RequestBody.create(CoinServerClient.writeToJsonString(createBuyOrderRequest), MediaType.get("application/json")))
                .addHeader("token", token)
                .build();

        return CoinServerClient.processRequest(request, new TypeReference<>() {
        }).isPresent();
    }

    public Boolean createSellOrder(CreateSellOrderRequest createSellOrderRequest) {

        Request request = new Request.Builder()
                .url(serverUrl + ORDERS_PREFIX + "/sail")
                .post(RequestBody.create(CoinServerClient.writeToJsonString(createSellOrderRequest), MediaType.get("application/json")))
                .addHeader("token", token)
                .build();

        return CoinServerClient.processRequest(request, new TypeReference<>() {
        }).isPresent();
    }

    public Boolean createSellAllOrder(CreateSellAllOrderRequest createSellAllOrderRequest) {

        Request request = new Request.Builder()
                .url(serverUrl + ORDERS_PREFIX + "/sail/all")
                .post(RequestBody.create(CoinServerClient.writeToJsonString(createSellAllOrderRequest), MediaType.get("application/json")))
                .addHeader("token", token)
                .build();

        return CoinServerClient.processRequest(request, new TypeReference<>() {
        }).isPresent();
    }
}