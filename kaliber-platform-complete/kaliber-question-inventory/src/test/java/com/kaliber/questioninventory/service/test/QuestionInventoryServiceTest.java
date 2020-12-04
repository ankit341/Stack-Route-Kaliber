package com.kaliber.questioninventory.service.test;

import com.kaliber.questioninventory.KaliberQuestionInventoryApplication;
import com.kaliber.questioninventory.exception.QuestionInventoryException;
import com.kaliber.questioninventory.model.AnswerOptions;
import com.kaliber.questioninventory.model.Concept;
import com.kaliber.questioninventory.model.Question;
import com.kaliber.questioninventory.service.IQuestionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = KaliberQuestionInventoryApplication.class)
@TestPropertySource("/application-test.properties")
public class QuestionInventoryServiceTest {

    @Autowired
    private IQuestionService quesService;

    private static Question question;

    @BeforeAll
    public static void questionSetup() throws QuestionInventoryException {

        ArrayList<String> name = new ArrayList<>();
        name.add("JAVA");
        Concept concept = new Concept (name, Concept.Taxonomy.Remember);
        ArrayList<String> answerKeys = new ArrayList<>();
        ArrayList<AnswerOptions> answerOptions = null;
        Date createdOn = new Date();
        Date updatedOn = new Date();

        question = new Question("what is java?", Question.QuestionType.MCQ, "Explain java",
                "Java", concept, 10, 2, Question.DifficultyLevel.Beginner,
                "No hint", "feedbackContent written", Question.Status.Draft, answerOptions,
                2, 1, answerKeys, true, createdOn, "ITC", updatedOn, "Rahul");
    }


    @Test
    public void getClientProfileDetails() throws QuestionInventoryException {
        int page =0;
        int limit = 1;
        quesService.createQuestion(question);
        UUID x= question.getQuestionId();
        try {
            assertEquals(1, quesService.getAllQuestions(page,limit).size());
        } catch (QuestionInventoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        question = quesService.getQuestionById(x);
        assertEquals("what is java?", question.getQuestionTitle());

    }

}