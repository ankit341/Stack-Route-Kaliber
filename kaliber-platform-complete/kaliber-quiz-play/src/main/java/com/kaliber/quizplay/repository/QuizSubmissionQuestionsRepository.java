package com.kaliber.quizplay.repository;

import com.kaliber.quizplay.model.QuizSubmissionQuestions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface QuizSubmissionQuestionsRepository extends MongoRepository<QuizSubmissionQuestions, Integer> {

     ArrayList<QuizSubmissionQuestions> findBySubmissionId(UUID submissionId);
     QuizSubmissionQuestions findBySubmissionIdAndSectionTitleAndQuestionId(UUID submissionId, String sectionTitle, UUID questionId);
     QuizSubmissionQuestions findBySubmissionIdAndSectionTitleAndSequence(UUID submissionId, String sectionTitle, int sequence);

}
