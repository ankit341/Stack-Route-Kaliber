package com.kaliber.quizplay.repository;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kaliber.quizplay.model.QuizSubmissionFeedback;

@Repository
public interface QuizPlayRepository extends MongoRepository<QuizSubmissionFeedback, Integer> {


	 ArrayList<QuizSubmissionFeedback> findByUserName(String userName, Pageable page);
	 ArrayList<QuizSubmissionFeedback> findByUserName(String userName);
     QuizSubmissionFeedback findBySubmissionId(UUID submissionId);
}
