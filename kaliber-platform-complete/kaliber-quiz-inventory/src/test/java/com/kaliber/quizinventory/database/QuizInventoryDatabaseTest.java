//package com.kaliber.quizinventory.database;
//
//import java.util.ArrayList;
//import java.util.Date;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.kaliber.quizinventory.exception.QuizNotFoundException;
//import com.kaliber.quizinventory.model.ConfidenceWager;
//import com.kaliber.quizinventory.model.Preferences;
//import com.kaliber.quizinventory.model.Quiz;
//import com.kaliber.quizinventory.model.Sections;
//import com.kaliber.quizinventory.model.Quiz.proficiencyCode;
//import com.kaliber.quizinventory.model.Quiz.quizTypeCode;
//import com.kaliber.quizinventory.model.Quiz.statusValue;
//import com.kaliber.quizinventory.model.Quiz.visibilityCode;
//import com.kaliber.quizinventory.repository.QuizRepository;
//
//@ExtendWith(SpringExtension.class)
//@DataMongoTest
//public class QuizInventoryDatabaseTest {
//	@Autowired
//	private QuizRepository quizRepo;
//
//	@BeforeEach
//	public void insert() {
//		Sections section=new Sections("A",1,false,false);
//		ArrayList<Sections> sectionArr=new ArrayList<Sections>();
//		sectionArr.add(section);
//		String[] concepts= {"Java","Angular"};
//		Date date=new Date();
//		ConfidenceWager wager=new ConfidenceWager("easy",50,30);
//		ArrayList<ConfidenceWager> confiWager=new ArrayList<ConfidenceWager>();
//		confiWager.add(wager);
//		Preferences preferences=new Preferences(false,false,false);
//		ArrayList<Preferences> prefer=new ArrayList<Preferences>();
//		prefer.add(preferences);
////		Quiz quiz=new Quiz("101","Java Beginners","ITC","ITC","ITC","Java",30,(float)100.0,"for java",sectionArr,statusCode.DRAFT,visibilityCode.PUBLIC,proficiencyCode.NOVICE,quizTypeCode.QUIZ,concepts,date,date,false,10,15,16,null, confiWager,prefer);
////		Quiz quiz=new Quiz("101","Java Beginners","ITC","ITC","ITC","Java",30,(float)100.0,"for java",sectionArr,concepts,date,date,false,10,15,16, confiWager,prefer);
//
////		quizRepo.save(quiz);
//	}
//
//	@Test
//	public void TestDb() throws QuizNotFoundException
//    {
////    	int expected = 1;
////    	int actual = quizRepo.findAll().size();
////    	Assertions.assertEquals(expected, actual);
//    }
//
//
//}
