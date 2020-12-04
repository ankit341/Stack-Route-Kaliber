package com.kaliber.leaderboard.repository;

import com.kaliber.leaderboard.model.LeaderBoard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface LeaderBoardRepository extends MongoRepository<LeaderBoard, UUID> {

    ArrayList<LeaderBoard> findByQuizCode(String quizCode);

}
