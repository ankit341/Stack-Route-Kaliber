package com.kaliber.quizplay.serviceproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "kaliber-semanticservice")
public interface MapUserToQuizServiceProxy {


    //Map Person to Quiz
    @PostMapping("/played")
    void mapPersonToQuiz(@RequestParam String username, @RequestParam String quizCode) ;


    //Map relation person [answered] question with parameter {answered correctly or incorrectly}.
    @PostMapping("/users/questions/answered")
    void userAnsweredQuestion(@RequestParam String username,
                                     @RequestParam UUID questionId);


}
