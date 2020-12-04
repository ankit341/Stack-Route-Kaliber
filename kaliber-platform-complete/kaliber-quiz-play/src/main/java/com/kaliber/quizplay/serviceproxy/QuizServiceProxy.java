package com.kaliber.quizplay.serviceproxy;

import com.kaliber.quizplay.model.QuizSectionQuestions;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@FeignClient(name="kaliber-quiz-inventory")
@RibbonClient(name="kaliber-quiz-inventory")
public interface QuizServiceProxy {

    @GetMapping("/quizzes")
    ResponseEntity<HashMap<String,Object>> getQuizList(@RequestParam(name="page",required = false) Integer page,
                                                               @RequestParam(name="limit",required = false) Integer limit,
                                                               @RequestParam(name="title",required = false) String title,
                                                               @RequestParam(name="quizCode",required = false) String quizCode,
                                                               @RequestParam(name="concepts",required = false) ArrayList<String> concepts,
                                                               @RequestParam(name="authoredBy",required = false) String authoredBy,
                                                               @RequestParam(name="subject",required = false) String subject
    );

    @GetMapping("/quizzes/{quizcode}/section/{sectiontitle}")
	ResponseEntity<Map<String,Object>> getAllQuizSectionQuestion(@PathVariable(name = "quizcode") String quizCode, @PathVariable(name="sectiontitle") String sectionTitle);

	@GetMapping("/quizzes/{quizCode}/sections")
	ArrayList<QuizSectionQuestions> getAllQuizSectionQuestionsByQuizCode(@PathVariable String quizCode);

	@GetMapping("/quizzes/{quizCode}/sections/{sectionTitle}/questions/{questionId}")
	QuizSectionQuestions getQuizSectionQuestion(@PathVariable String quizCode,@PathVariable String sectionTitle,@PathVariable UUID questionId);
}

