package com.kaliber.questioninventory.repository;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kaliber.questioninventory.model.Question;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String>{
	
	Question findByQuestionId(UUID questionId);

	ArrayList<Question> findByDifficultyLevel(String difficultyLevel);

	ArrayList<Question> findBySubject(String subject);

}