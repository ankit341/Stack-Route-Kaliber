//package com.kaliber.quizinventory.repository;
//
//import com.kaliber.quizinventory.exception.QuizNotFoundException;
//import com.kaliber.quizinventory.model.Quiz;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class QuizRepositoryImpl implements QuizRepository {
//
//    @Autowired
//    MongoTemplate mongoTemplate;
//
//
//    @Override
//    public List<Quiz> findAll(Integer page, Integer limit) throws QuizNotFoundException {
//        PageRequest pageable =  PageRequest.of(page, limit);
//        if(mongoTemplate.findAll(Quiz.class).isEmpty())
//        {
//            throw new QuizNotFoundException("Quiz not found");
//        }
//
//        Query query = new Query();
//        query.skip(page * limit);
//        query.limit(limit);
//
//        return mongoTemplate.find(query,Quiz.class);
//    }
//
//    @Override
//    public Quiz save(Quiz quiz) {
//        this.mongoTemplate.save(quiz);
//        return quiz;
//    }
//
//    @Override
//    public Quiz modify(Quiz quiz, String quizCode) {
//        quiz.setQuizCode(quizCode);
//        mongoTemplate.save(quiz);
//        return quiz;
//    }
//
//    @Override
//    public int getCountOfQuizzes() {
//        return mongoTemplate.findAll(Quiz.class).size();
//    }
//
//    @Override
//    public ArrayList<Quiz> getByQuizList(String title, String quizCode, ArrayList<String> concepts, String authoredBy, String subject) {
//        System.out.println("title is " + title);
//        System.out.println("quizCode is " + quizCode);
//        System.out.println("concepts is " + concepts);
//        System.out.println("authoredBy is " + authoredBy);
//        System.out.println("subject is " + subject);
//        Query query =new Query();
//        if(title!=null){
//            query.addCriteria(Criteria.where("title").is(title));
//        }
//        if(quizCode!=null){
//            query.addCriteria(Criteria.where("quizCode").is(quizCode));
//        }
//        if(concepts!=null){
//            query.addCriteria(Criteria.where("concepts").is(concepts));
//        }
//        if(authoredBy!=null){
//            query.addCriteria(Criteria.where("authoredBy").is(authoredBy));
//        }
//        if(subject!=null){
//            query.addCriteria(Criteria.where("subject").is(subject));
//        }
//        ArrayList<Quiz> result = (ArrayList<Quiz>)mongoTemplate.find(query, Quiz.class);
//        System.out.println("QUIZ LIST IS" + result);
//        return result;
//    }
//}​​
package com.kaliber.quizinventory.repository;

import com.kaliber.quizinventory.exception.QuizNotFoundException;
import com.kaliber.quizinventory.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizRepositoryImpl implements QuizRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Quiz findByQuizCode(String quizCode) throws QuizNotFoundException {
//        System.out.println("before");
        Query query =new Query();
        query.addCriteria(Criteria.where("quizCode").is(quizCode));
//        System.out.println("after"+quizCode);
        Quiz quiz = (Quiz) mongoTemplate.findOne(query,Quiz.class);
//        S ystem.out.println("QUIZIN REPO IS "+quiz);
        return quiz;
    }

    @Override
    public List<Quiz> findAll(Integer page, Integer limit) throws QuizNotFoundException {

        if(mongoTemplate.findAll(Quiz.class).isEmpty())
        {
            throw new QuizNotFoundException("Quiz not found");
        }

        Query query = new Query();
        query.skip(page * limit);
        query.limit(limit);
        System.out.println("page and limit"+page+limit);
        return mongoTemplate.find(query,Quiz.class);
    }

    @Override
    public Quiz save(Quiz quiz) {
        this.mongoTemplate.save(quiz);
        return quiz;
    }

    @Override
    public Quiz modify(Quiz quiz, String quizCode) {
        quiz.setQuizCode(quizCode);
        System.out.println("before modify");
        this.mongoTemplate.save(quiz);
        System.out.println("after modify");
        return quiz;
    }

    @Override
    public int getCountOfQuizzes() {
        return mongoTemplate.findAll(Quiz.class).size();
    }

    @Override
    public ArrayList<Quiz> getByQuizList(Integer page, Integer limit, String title, String quizCode, ArrayList<String> concepts, String authoredBy, String subject, Quiz.statusValue statusvalue) {
//        System.out.println("page " + page);
//        System.out.println("limit " + limit);
        System.out.println("title is " + title);
        System.out.println("quizCode is " + quizCode);
        System.out.println("concepts is " + concepts);
        System.out.println("authoredBy is " + authoredBy);
        System.out.println("subject is " + subject);
        Query query =new Query();
        query.skip(page * limit);
        query.limit(limit);
//        query.addCriteria(Criteria.where("page").is(page));
//        query.addCriteria(Criteria.where("limit").is(limit));
        if(title!=null){
            query.addCriteria(Criteria.where("title").is(title));
        }
        if(quizCode!=null){
            query.addCriteria(Criteria.where("quizCode").is(quizCode));
        }
        if(concepts!=null){
            query.addCriteria(Criteria.where("concepts").is(concepts));
        }
        if(authoredBy!=null){
            query.addCriteria(Criteria.where("authoredBy").is(authoredBy));
        }
        if(subject!=null){
            query.addCriteria(Criteria.where("subject").is(subject));
        }
        if(statusvalue!=null){
            query.addCriteria(Criteria.where("statusvalue").is(statusvalue));
        }
        ArrayList<Quiz> result = (ArrayList<Quiz>)mongoTemplate.find(query, Quiz.class);
        System.out.println("QUIZ LIST IS" + result);
        return result;
    }
}