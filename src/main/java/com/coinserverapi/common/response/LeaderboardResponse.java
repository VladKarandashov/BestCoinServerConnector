package com.coinserverapi.common.response;

import com.coinserverapi.common.dto.UserScore;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardResponse {

    private List<UserScore> userScoreList;
}