package com.kaliber.semanticservice.controller;

import com.kaliber.semanticservice.model.User;
import com.kaliber.semanticservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    UserService userService;


    //Create person node
    //API will be called when the user comes to the platform for the first time.
    @PostMapping("/users")
    public User createPersonNode(@RequestParam(value = "username") String username) {
        return userService.createPersonNode(username);
    }


    //Map Person to Quiz with relation User [PLAYED] Quiz
    @PostMapping("/played")
    public void mapPersonToQuiz(@RequestParam String username,
                                @RequestParam String quizCode) {
        userService.mapPersonToQuiz(username, quizCode);
    }


    //To map the relation User [FOLLOWING] User
    @PostMapping("/users/following")
    public void userFollowingUser(@RequestParam String username,
                                      @RequestParam String personName){
        System.out.println("in semantic service--- follow user relation " + username+ " friend" + personName);
        userService.userFollowingUser(username,personName);
    }


    //TO map the relation User [INTRESTED_IN] Subject
    @PostMapping("users/following/subjects")
    public void userFollowingSubject(@RequestParam String username,
                                     @RequestBody HashSet<String> subject){
        userService.userFollowingSubject(username,subject);
    }



    //For Quiz Feed1.
    ////Suggesting quizzes that are played by user whom he is following.
    @GetMapping("/users/quizzes/suggest")
    public  ArrayList<HashMap<String,String>> getSuggestedQuiz(@RequestParam("username") String username){
        return userService.getSuggestedQuizzes(username);
    }


    //For Quiz Feed2 (updated)
    //Seggested quizzes based on the user Interested Subject(Topic)
    @GetMapping("users/quizzes/on/followedsubjects")
    public ArrayList<HashMap<String,String>> getQuizzesBasedOnSubjects(@RequestParam("username") String username){
        return userService.getQuizzesBasedOnSubjects(username);
    }


    //Map relation person [answered] question with parameter {answered correctly or incorrectly}.
    @PostMapping("/users/questions/answered")
    public void userAnsweredQuestion(@RequestParam String username,
                                       @RequestParam UUID questionId) {
        userService.userAnsweredQuestion(username,questionId);
    }



    //    @PostMapping("/users")
//    public ResponseEntity<?> createPersonNode(@RequestParam(value = "username") String username) {
//        User newUser = null;
//        HashMap<String, Object> response= new HashMap<>();
//        try {
//            newUser = userService.createPersonNode(username);
//            response.put("message","Successfully reterived question with questionID: "+newUser);
//            response.put("count", 1);
//            response.put("result", newUser);
//            response.put("statusCode", HttpStatus.OK);
//        }catch (Exception qe) {
////            logger.error(qe.getMessage());
//            response.put("message", "Can't retreived the question with questionID: "+ newUser);
//            response.put("count", 1);
//            response.put("result", qe.getMessage());
//            response.put("statusCode", HttpStatus.OK);
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(response, HttpStatus.OK);
//
//    }

    //Fetch all the user who have played the quiz
//    @GetMapping("/persons")
//    public Collection<User> getAllPerson(){
//        return userService.getAllPerson();
//    }
//
//
//
//    @GetMapping("/questions/concept")
//    public ArrayList<Question> SearchQuestionByConcept(@RequestParam(value = "concept") String concept,
//                                                       @RequestParam(value = "taxonomy") String taxonomy,
//                                                       @RequestParam(value = "difficultyLevel") String difficultyLevel){
//        return userService.SearchQuestionByConcept(concept,taxonomy,difficultyLevel);
//    }


}
