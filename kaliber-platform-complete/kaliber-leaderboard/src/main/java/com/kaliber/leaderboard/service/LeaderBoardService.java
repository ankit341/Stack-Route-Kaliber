package com.kaliber.leaderboard.service;

import com.kaliber.leaderboard.exception.LeaderBoardException;
import com.kaliber.leaderboard.model.LeaderBoard;
import com.kaliber.leaderboard.model.User;

import java.util.ArrayList;

public interface LeaderBoardService {

    ArrayList<LeaderBoard> getLeaderBoardByQuizCode(String quizCode) throws LeaderBoardException;

    LeaderBoard saveNewUserToLeaderBoard(LeaderBoard leaderBoard) throws LeaderBoardException;

    LeaderBoard updateExistingUserInLeaderBoard(String username, String quizCode , LeaderBoard leaderBoardEntry) throws LeaderBoardException;

    User getUserByUserName(String username) throws LeaderBoardException;

    int getCountOfEntriesInLeaderBoard(String quizCode);

}
