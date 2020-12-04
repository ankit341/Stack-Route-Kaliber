package com.kaliber.quizinventory.service;

import java.util.ArrayList;
import java.util.UUID;

import com.kaliber.quizinventory.serviceproxy.QuizServiceProxxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaliber.quizinventory.exception.QuizSectionQuestionNotFoundException;
import com.kaliber.quizinventory.model.QuizSectionQuestion;
import com.kaliber.quizinventory.repository.QuizSectionQuestionRepository;

@Service
public class QuizSectionQuestionService implements QuizSectionQuestionServiceInterface {

	@Autowired
	private QuizSectionQuestionRepository repo;

	@Autowired
	QuizServiceProxxy quizServiceProxxy;

	/* This methd finds all the quizzes present
		accesses the repository with the object
	 */
	public ArrayList<QuizSectionQuestion> findAll() throws QuizSectionQuestionNotFoundException{
		return (ArrayList<QuizSectionQuestion>) repo.findAll();
	}
	/* This method finds all the quizzes present with parameter quizcode and sectionttitle
		accesses the repository with the object
	 */
	public ArrayList<QuizSectionQuestion> findByQuizCodeAndSectionTitle(String quizCode, String sectionTitle)
			throws QuizSectionQuestionNotFoundException{
		return repo.findByQuizCodeAndSectionTitle(quizCode, sectionTitle);
	}

	/* This method posts the the quiz with quizcode and sectionttitle
		accesses the repository with the object
	 */
	public QuizSectionQuestion save(QuizSectionQuestion quizSectionQuestion) throws QuizSectionQuestionNotFoundException{
//		UUID questionId=UUID.randomUUID();
//		quizSectionQuestion.setQuestionId(questionId);
		QuizSectionQuestion quizSectionQuestionDetails = repo.save(quizSectionQuestion);
		if(quizSectionQuestion !=null){
			System.out.println("inside loop::::::::::::::::::__>");
			System.out.println("Inside quizSectionQuestion :: ---> "+ quizSectionQuestion.getQuestionId()+
					"quiz code :: --->" + quizSectionQuestion.getQuizCode());
//			quizServiceProxxy.questionAskedInQuiz(quizSectionQuestionDetails.getQuestionId(),
//					quizSectionQuestionDetails.getQuizCode());
		}
		System.out.println("outside if loop:: --> ");
		return quizSectionQuestionDetails;
	}

	/* This method returns the count of the the quizSectionQuestion entity
	 */
    public int getCountOfQuestions()
	{
		return repo.findAll().size();
    }


	public ArrayList<QuizSectionQuestion> findByQuizCode(String quizCode){

		return repo.findByQuizCode(quizCode);

	}

	public QuizSectionQuestion findByQuizCodeAndSectionTitleAndQuestionId(String quizCode, String sectionTitle, UUID questionId){

    	return repo.findByQuizCodeAndSectionTitleAndQuestionId(quizCode,sectionTitle,questionId);

	}

}
