package com.kaliber.leaderboard.controller;

import com.kaliber.leaderboard.exception.LeaderBoardException;
import com.kaliber.leaderboard.model.LeaderBoard;
import com.kaliber.leaderboard.service.LeaderBoardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LeaderBoardController {

    @Autowired
    LeaderBoardService leaderBoardService;

    public static Logger logger = LogManager.getLogger(LeaderBoardService.class);

    @GetMapping("/leaderboard/{quizCode}")
    public ResponseEntity<?> getLeaderBoardByQuizCode(@PathVariable(value = "quizCode") String quizCode) {
//        ArrayList<LeaderBoard> leaderBoard;
        try {
            ArrayList<LeaderBoard>leaderBoard = leaderBoardService.getLeaderBoardByQuizCode(quizCode);
            System.out.println("LEADERBOARD IN CONTROLLER IS " + leaderBoard);
            Map<String,Object> response = new HashMap<String, Object>();
            response.put("error",false);
            response.put("message" , "Leaderboard for quiz code" + quizCode);
            response.put("result" , leaderBoard);
            System.out.println("RESPONSE IS " + response);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (LeaderBoardException leaderException){
            logger.error("No quiz with this quizcode" + quizCode);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/leaderboard")
    public ResponseEntity<?> saveNewUserInLeaderBoard(@RequestBody LeaderBoard leaderBoardEntry ) throws DuplicateKeyException{
        try{
            System.out.println("IN LEADERBOARD POST AND data is " + leaderBoardEntry);
            LeaderBoard leaderBoard = leaderBoardService.saveNewUserToLeaderBoard(leaderBoardEntry);
            Map<String,Object> response = new HashMap<String, Object>();
            response.put("error",false);
            response.put("message","Leaderboard saved is"+leaderBoardEntry.toString());
            response.put("result",leaderBoard);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (LeaderBoardException leaderException){
            logger.error("No leaderbaord object came like" + leaderBoardEntry.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/leaderboard/updateuser")
    public ResponseEntity<?> updateExistingUserDataOfParticularQuiz(@RequestParam String username, @RequestParam String quizCode, @RequestBody LeaderBoard leaderBoard) throws LeaderBoardException {
        try{
            LeaderBoard leaderBoard1 = leaderBoardService.updateExistingUserInLeaderBoard(username, quizCode,leaderBoard);
            Map<String,Object> response = new HashMap<>();
            response.put("error",false);
            response.put("message","Leaderboard is updated perfectly");
            response.put("result",leaderBoard1);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (LeaderBoardException leaderException){
            logger.error("Problem in upating recode with quizcode" + quizCode + "and with username" + username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
