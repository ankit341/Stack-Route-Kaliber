package com.kaliber.usermanagement.serviceproxy;

import com.kaliber.usermanagement.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;


@FeignClient(name="kaliber-semanticservice")
public interface SemanticServiceProxy {

    //Create person node
    //API will be called when the user comes to the platform for the first time.
    @PostMapping("/users")
    User createPersonNode(@RequestParam(value = "username") String username);


    //Creating relationship USer following User.
    @PostMapping("/users/following")
    void userFollowingUser(@RequestParam String username,
                                  @RequestParam String personName);


    //Creating relationship User [INTERESTED_IN] Subject.
    @PostMapping("users/following/subjects")
    void userFollowingSubject(@RequestParam String username,
                              @RequestBody HashSet<String> subject);

}
