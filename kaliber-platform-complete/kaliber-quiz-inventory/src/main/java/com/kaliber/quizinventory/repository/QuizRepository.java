//package com.kaliber.quizinventory.repository;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.kaliber.quizinventory.exception.QuizNotFoundException;
//import com.kaliber.quizinventory.service.QuizServiceInterface;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//
//import com.kaliber.quizinventory.model.Quiz;
//
//@Repository
//public interface QuizRepository {
//
//
//
//	//Quiz findByQuizId(String quizcode);
////	public ArrayList<Quiz> findAll();
//
////	Quiz findByQuizCode(String quizCode);
////
////	ArrayList<Quiz> findByCreatedBy(String createdBy);
//
////	ContentHandler findAll(Pageable );
//
////	ArrayList<Quiz> getQuizList(String title,String quizCode,ArrayList<String> concepts,String authoredBy,String subject);
//    List<Quiz> findAll(Integer page, Integer limit) throws QuizNotFoundException;
//
//    Quiz save(Quiz quiz);
//
//    Quiz modify(Quiz quiz, String quizCode);
//
//    int getCountOfQuizzes();
//
//    ArrayList<Quiz> getByQuizList(String title,String quizCode,ArrayList<String> concepts,String authoredBy,String subject);
//}
package com.kaliber.quizinventory.repository;


import java.util.ArrayList;
import java.util.List;

import com.kaliber.quizinventory.exception.QuizNotFoundException;
import org.springframework.stereotype.Repository;

import com.kaliber.quizinventory.model.Quiz;

@Repository
public interface QuizRepository {



    //Quiz findByQuizId(String quizcode);
//	public ArrayList<Quiz> findAll();

	Quiz findByQuizCode(String quizCode) throws QuizNotFoundException;
//
//	ArrayList<Quiz> findByCreatedBy(String createdBy);

//	ContentHandler findAll(Pageable );

    //	ArrayList<Quiz> getQuizList(String title,String quizCode,ArrayList<String> concepts,String authoredBy,String subject);
    List<Quiz> findAll(Integer page, Integer limit) throws QuizNotFoundException;

    Quiz save(Quiz quiz);

    Quiz modify(Quiz quiz, String quizCode);

    int getCountOfQuizzes();

    ArrayList<Quiz> getByQuizList(Integer page, Integer limit, String title, String quizCode, ArrayList<String> concepts, String authoredBy, String subject, Quiz.statusValue statusvalue);
}