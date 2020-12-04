package com.kaliber.quizplay.test;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kaliber.quizplay.exception.QuizPlayException;
import com.kaliber.quizplay.model.QuizSubmissionFeedback;
import com.kaliber.quizplay.repository.QuizPlayRepository;

//@ExtendWith(SpringExtension.class)
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class QuizPlayServiceTests {

	@Autowired
	QuizPlayRepository quizRepo;
	
    @BeforeEach
	public void insertDocument()
	{
    	QuizSubmissionFeedback quizSub = new QuizSubmissionFeedback(null, "Rev981","Java001",30,21,8,2,100, null, null, null, null);
        UUID uuid = UUID.randomUUID();
        quizSub.setSubmissionId(uuid);
    	quizRepo.save(quizSub);
    	System.out.println("kjsdbkjsskjns");
	}
    
    @AfterEach
    public void deleteDocument() 
    {
    	quizRepo.deleteAll();
    }
    
    @Test
    public void testRetrieval() throws QuizPlayException
    {
    	int expected = 1;
    	int actual = quizRepo.findAll().size();
    	Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void testRetrievalFail() throws QuizPlayException
    {
    	int expected = 5;
    	int actual = quizRepo.findAll().size();
    	Assertions.assertNotEquals(expected, actual);
    }

    @Test
    public void testRetrievalByUserName()
    {
    	int expected = 1;
    	int actual = quizRepo.findByUserName("Rev981", Pageable.unpaged()).size();
    	Assertions.assertEquals(expected, actual);
    }
    
    
}
