package com.kaliber.questioninventory.serviceproxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.UUID;

@FeignClient(name="kaliber-semanticservice")
public interface SemanticServiceProxy {

    //Create Question node
    @PostMapping("/questions")
    ResponseEntity<?> createQuestionNode(@RequestParam(value = "questionId") UUID questionId);

    @PostMapping("/concept")
    ResponseEntity<?> createConceptNode(@RequestBody ArrayList<String> concept);


    //mapConceptToQuestion
    @PostMapping("/map-concept-to-question")
    void mapConceptToQuestion(@RequestParam UUID questionId,
                                     @RequestBody ArrayList<String> concept,
                                     @RequestParam String taxonomy,
                                     @RequestParam String difficultyLevel );

}
