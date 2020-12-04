package com.kaliber.leaderboard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaliber.leaderboard.exception.LeaderBoardException;
import com.kaliber.leaderboard.model.LeaderBoard;
import com.kaliber.leaderboard.model.User;
import com.kaliber.leaderboard.repository.LeaderBoardRepository;
import com.kaliber.leaderboard.serviceProxy.UserServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

    @Autowired
    LeaderBoardRepository leaderBoardRepository;
    @Autowired
    UserServiceProxy userServiceProxy;

    @Override
    public ArrayList<LeaderBoard> getLeaderBoardByQuizCode(String quizCode) throws LeaderBoardException {
        if(leaderBoardRepository.findByQuizCode(quizCode).toString().isEmpty())
            throw new LeaderBoardException("No quiz found with this quiz code" + quizCode);
        ArrayList<LeaderBoard> leaderBoard = leaderBoardRepository.findByQuizCode(quizCode);
        System.out.println("leaderboard is" + leaderBoard);
        leaderBoard.sort((leaderBoard1, leaderBoard2 ) -> Float.valueOf(leaderBoard2.getUserPoints()).compareTo(Float.valueOf(leaderBoard1.getUserPoints())));
        return leaderBoard;
    }

    @Override
    public LeaderBoard saveNewUserToLeaderBoard( LeaderBoard leaderBoard) throws LeaderBoardException{
        if(leaderBoard.toString().isEmpty())
            throw new LeaderBoardException("Nothing recieved in the leaderboard object");
        ArrayList<LeaderBoard> leaderBoardArrayList = leaderBoardRepository.findByQuizCode(leaderBoard.getQuizCode());
        boolean userExist = false;
        LeaderBoard leaderBoardAlreadyExistUser = null;
        LeaderBoard leaderBoardNewUser = new LeaderBoard();
        LeaderBoard leaderBoard1 = null;
        for(LeaderBoard leaderBoardTemp: leaderBoardArrayList){
            if(leaderBoardTemp.getUsername().equals(leaderBoard.getUsername())){
                userExist = true;
                leaderBoardAlreadyExistUser = leaderBoardTemp;
            }
        }
        if(userExist){
            System.out.println("inside if userexists");
            leaderBoardAlreadyExistUser.setUserPoints(leaderBoard.getUserPoints());
            leaderBoardAlreadyExistUser.setQuizTakenTime(leaderBoard.getQuizTakenTime());
            leaderBoardAlreadyExistUser.setUserStarRating(leaderBoard.getUserPoints()*20/100);
            leaderBoardAlreadyExistUser.setPositionChange(0);
            leaderBoard1 = leaderBoardRepository.save(leaderBoardAlreadyExistUser);
        } else {
            System.out.println("inside else");
            String username = leaderBoardNewUser.setUsername(leaderBoard.getUsername());
            User responseName = getUserByUserName(username);
            leaderBoardNewUser.setUserAvatar(responseName.getAvatarURL());
            System.out.println("username " +username);
            System.out.println("RESPONSENAME IS " + responseName);
            leaderBoardNewUser.setName(responseName.getName());
            leaderBoardNewUser.setUserPoints(leaderBoard.getUserPoints());
            leaderBoardNewUser.setQuizTakenTime(leaderBoard.getQuizTakenTime());
            leaderBoardNewUser.setUserStarRating(leaderBoard.getUserPoints()*20/100);
            leaderBoardNewUser.setQuizCode(leaderBoard.getQuizCode());
            leaderBoardNewUser.setPositionChange(0);
            System.out.println("response is user image" + responseName.getAvatarURL());
            leaderBoard1 = leaderBoardRepository.save(leaderBoardNewUser);
        }
        return leaderBoard1;
    }

    @Override
    public LeaderBoard updateExistingUserInLeaderBoard(String username, String quizCode, LeaderBoard leaderBoardentry) throws LeaderBoardException{
        if(quizCode == null || username == null)
            throw new LeaderBoardException("There is no username and quizcode in request");
        ArrayList<LeaderBoard> leaderBoard = leaderBoardRepository.findByQuizCode(quizCode);
        System.out.println("LEADERBOARD IS "+leaderBoard);
        for ( LeaderBoard leaderboardtemp: leaderBoard) {
            System.out.println("inside for loop");
            if(leaderboardtemp.getUsername().equals(username)){
                System.out.println("inside if condition ");
                leaderboardtemp.setUserPoints(leaderBoardentry.getUserPoints());
                leaderboardtemp.setQuizTakenTime(leaderBoardentry.getQuizTakenTime());
                float userRating = leaderBoardentry.getUserPoints()/20;
                leaderboardtemp.setUserStarRating(userRating);
                System.out.println("before leaderboard origin");
                return leaderBoardRepository.save(leaderboardtemp);
            }
        }
        System.out.println("Before null");
        return null;
    }

    @Override
    public User getUserByUserName(String username) throws LeaderBoardException {
        System.out.println("naME"+username);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("body"+userServiceProxy.getByUserName(username).getBody());
        User response = mapper.convertValue(userServiceProxy.getByUserName(username).getBody().get("result"), User.class);

        System.out.println("rsponse "+response);
        return response;
    }

    @Override
    public int getCountOfEntriesInLeaderBoard(String quizCode) {
        return 0;
    }


}