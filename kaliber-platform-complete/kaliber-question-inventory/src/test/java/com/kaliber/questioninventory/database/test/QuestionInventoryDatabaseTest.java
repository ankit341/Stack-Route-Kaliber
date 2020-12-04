package com.kaliber.questioninventory.database.test;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kaliber.questioninventory.exception.QuestionInventoryException;
import com.kaliber.questioninventory.model.AnswerOptions;
import com.kaliber.questioninventory.model.Concept;
import com.kaliber.questioninventory.model.Concept.Taxonomy;
import com.kaliber.questioninventory.model.Question;
import com.kaliber.questioninventory.model.Question.DifficultyLevel;
import com.kaliber.questioninventory.model.Question.QuestionType;
import com.kaliber.questioninventory.model.Question.Status;
import com.kaliber.questioninventory.repository.QuestionRepository;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class QuestionInventoryDatabaseTest {

    @Autowired
    private QuestionRepository questionRepo;

    @BeforeEach
    public void insertQuestion() {
        ArrayList<String> name = new ArrayList<>();
        name.add("JAVA");
        Concept concept = new Concept (name , Taxonomy.Remember);
        ArrayList<String> answerKeys = new ArrayList<>();
        answerKeys.add("1");
        answerKeys.add("2");
        AnswerOptions answerOptn = new AnswerOptions("1","java is prog. lang.","java.doc");
        ArrayList<AnswerOptions> answerOptions = new ArrayList<>();
        answerOptions.add(answerOptn);

        Date createdOn = new Date();
        Date updatedOn = new Date();


        Question question = new Question("what is java?", QuestionType.MCQ , "Explain java",
                "Java", concept , 10, 2, DifficultyLevel.Beginner,
                "No hint", "feedbackContent written", Status.Draft	, answerOptions,
                2, 1, answerKeys , true ,createdOn, "ITC",  updatedOn, "Rahul");

        questionRepo.save(question);
    }

    @Test
    public void TestDatabase() throws QuestionInventoryException {
        int expected =1;
        int actual = questionRepo.findAll().size();

        Assertions.assertEquals(expected, actual);

    }



}


