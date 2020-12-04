package com.kaliber.quizplay.serviceproxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.UUID;

@FeignClient(name="kaliber-question-inventory")
@RibbonClient(name="kaliber-question-inventory")
public interface QuestionServiceProxy {
	@GetMapping("/questions/{questionId}")
	ResponseEntity<Map<String, Object>> getQuestionById(@PathVariable("questionId") UUID questionId);
}
