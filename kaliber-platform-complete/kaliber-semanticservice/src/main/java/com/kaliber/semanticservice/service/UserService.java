package com.kaliber.semanticservice.service;

import com.kaliber.semanticservice.model.User;
import com.kaliber.semanticservice.repository.QuizRepo;
import com.kaliber.semanticservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    QuizRepo quizRepo;


    public User createPersonNode(String username) {
        System.out.println("in sematic service service class..." + username);
        return userRepo.createPersonNode(username);
    }

    public void mapPersonToQuiz(String username, String quizCode) {
        userRepo.mapPersonToQuiz(username, quizCode);
    }


    public void userFollowingUser(String username, String personName) {
        userRepo.userFollowingUser(username,personName);
    }

    public void userFollowingSubject(String username, HashSet<String> subject) {
        userRepo.userFollowingSubject(username,subject);
    }

    //For quiz feed
    //Suggesting quizzes that are played by user whom he is following.
    public  ArrayList<HashMap<String,String>> getSuggestedQuizzes(String username) {
        return userRepo.getSuggestedQuizzes(username);
    }

    //quizzes of those subject in which user is intrested in..
    public ArrayList<HashMap<String,String>> getQuizzesBasedOnSubjects(String username) {
        return userRepo.getQuizzesBasedOnSubjects(username);
    }


    public void userAnsweredQuestion(String username, UUID questionId) {
        userRepo.userAnsweredQuestion(username,questionId);
    }

//    public Collection<User> getAllPerson() {
//        return userRepo.getAllPerson();
//    }
//
//
//    public ArrayList<Question> SearchQuestionByConcept(String concept, String taxonomy, String difficultyLevel) {
//        return userRepo.SearchQuestionByConcept(concept,taxonomy,difficultyLevel);
//    }
}
