package com.kaliber.quizinventory.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import com.kaliber.quizinventory.model.QuizSectionQuestionQuery;

@Repository
public interface QuizSectionQuestionQueryRepository extends MongoRepository<QuizSectionQuestionQuery,Integer>{

}
