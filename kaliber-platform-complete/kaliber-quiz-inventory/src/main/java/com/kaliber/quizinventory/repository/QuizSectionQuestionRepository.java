package com.kaliber.quizinventory.repository;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kaliber.quizinventory.model.QuizSectionQuestion;

@Repository
public interface QuizSectionQuestionRepository extends MongoRepository<QuizSectionQuestion,Integer>{

//	ArrayList<QuizSectionQuestion> findByquizcode(String quizcode);

	ArrayList<QuizSectionQuestion> findByQuizCodeAndSectionTitle(String quizCode, String sectionTitle);

    ArrayList<QuizSectionQuestion> findByQuizCode(String quizCode);

	QuizSectionQuestion findByQuizCodeAndSectionTitleAndQuestionId(String quizCode, String sectionTitle, UUID questionId);
}
