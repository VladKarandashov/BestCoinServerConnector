package com.coinserverapi.client;

import com.coinserverapi.common.GenericResponse;
import com.coinserverapi.common.exception.CoinServerException;
import com.coinserverapi.common.exception.ExceptionBasis;
import com.coinserverapi.common.request.LoginRequest;
import com.coinserverapi.common.response.LoginResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Objects;
import java.util.Optional;

public class CoinServerClient {

    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private String token;

    public final PricesClient prices;
    public final StatisticsClient statistics;
    public final BalanceClient balance;
    public final OrdersClient orders;

    private final AuthClient authClient;

    public CoinServerClient(String serverIP, String serverPort) {
        String serverUrl = "http://" + serverIP + ":" + serverPort;
        prices = new PricesClient(serverUrl);
        statistics = new StatisticsClient(serverUrl);
        authClient = new AuthClient(serverUrl);
        balance = new BalanceClient(serverUrl);
        orders = new OrdersClient(serverUrl);
    }

    public void login(LoginRequest loginRequest) {
        token = authClient.login(loginRequest)
                .map(LoginResponse::getToken)
                .orElseThrow(() -> new CoinServerException(ExceptionBasis.INTERNAL_SERVER_ERROR));
        balance.setToken(token);
        orders.setToken(token);
    }

    public void registration(LoginRequest loginRequest) {
        token = authClient.registration(loginRequest)
                .map(LoginResponse::getToken)
                .orElseThrow(() -> new CoinServerException(ExceptionBasis.INTERNAL_SERVER_ERROR));
        balance.setToken(token);
        orders.setToken(token);
    }

    public static <T> Optional<T> processRequest(Request request, TypeReference<GenericResponse<T>> typeReference) {
        return callOpt(request)
                .map(body -> {
                    try {
                        return mapper.readValue(body, typeReference);
                    } catch (JsonProcessingException e) {
                        throw new CoinServerException(ExceptionBasis.INTERNAL_SERVER_ERROR, e);
                    }
                })
                .map(CoinServerClient::processGenericResponse);
    }

    public static Optional<String> callOpt(Request request) {
        return Optional.ofNullable(call(request));
    }

    private static String call(Request request) {
        try {
            return Objects.requireNonNull(httpClient.newCall(request).execute().body()).string();
        } catch (Exception e) {
            System.out.println("Не удалось выполнить запрос " + request.toString());
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T processGenericResponse(GenericResponse<T> response) {

        if (response.getStatusCode() != 0) {
            throw new CoinServerException(ExceptionBasis.from(response.getStatusCode()));
        }
        return response.getData();
    }

    public static String writeToJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new CoinServerException(ExceptionBasis.INTERNAL_SERVER_ERROR, e);
        }
    }
}
