package com.kaliber.quizplay.serviceproxy;

import com.kaliber.quizplay.model.LeaderBoard;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="kaliber-leaderboard")
@RibbonClient(name="kaliber-leaderboard")
public interface LeaderBoardServiceProxy {
    @PostMapping("/leaderboard")
     ResponseEntity<?> saveNewUserInLeaderBoard(@RequestBody LeaderBoard leaderBoardEntry );
}
