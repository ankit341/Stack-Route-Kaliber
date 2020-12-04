package com.kaliber.leaderboard.serviceProxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name="kaliber-user-management")
@RibbonClient(name="kaliber-user-management")
public interface UserServiceProxy {

    @GetMapping("/users/{username}")
    ResponseEntity<Map<String, Object>> getByUserName(@PathVariable("username") String userName);
}
