package com.coinserverapi.client;

import com.coinserverapi.common.response.LeaderboardResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.Request;

import java.util.Optional;

public class StatisticsClient {

    private final String serverUrl;

    protected StatisticsClient(String serverUrl) {
        this.serverUrl = serverUrl;
    }


    private static final String STATISTICS_PREFIX = "/api/v1/statistics";

    public Optional<LeaderboardResponse> getLeaderboard() {
        Request request = new Request.Builder()
                .url(serverUrl + STATISTICS_PREFIX + "/leaderboard")
                .build();

        return CoinServerClient.processRequest(request, new TypeReference<>() {
        });
    }
}
