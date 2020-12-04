//package com.kaliber.quizinventory.service.test;
//
//
//import com.kaliber.quizinventory.exception.QuizNotFoundException;
//import com.kaliber.quizinventory.model.ConfidenceWager;
//import com.kaliber.quizinventory.model.Preferences;
//import com.kaliber.quizinventory.model.Quiz;
//import com.kaliber.quizinventory.model.Sections;
//import com.kaliber.quizinventory.repository.QuizRepository;
//import com.kaliber.quizinventory.service.QuizService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class QuizServiceTest {
//    @Mock
//    private QuizRepository quizRepository;
//
//    @BeforeEach
//    public void init(){
//        MockitoAnnotations.initMocks(this);
//    }
//
////    @Test
//    public void getAllQuizzesTest() throws QuizNotFoundException {
//
//        ArrayList<Quiz> quizArrayList = new ArrayList<Quiz>();
//        Sections section=new Sections("A",1,false,false);
//        ArrayList<Sections> sectionArr=new ArrayList<Sections>();
//        sectionArr.add(section);
//        String[] concepts= {"Java","Angular"};
//        Date date=new Date();
//        ConfidenceWager wager=new ConfidenceWager("easy",50,30);
//        ArrayList<ConfidenceWager> confiWager=new ArrayList<ConfidenceWager>();
//        confiWager.add(wager);
//        Preferences preferences=new Preferences(false,false,false);
//        ArrayList<Preferences> prefer=new ArrayList<Preferences>();
//        prefer.add(preferences);
////        Quiz quiz=new Quiz("101","Java Beginners","ITC","ITC","ITC","Java",30,(float)100.0,"for java",sectionArr, Quiz.statusCode.DRAFT, Quiz.visibilityCode.PUBLIC, Quiz.proficiencyCode.NOVICE, Quiz.quizTypeCode.QUIZ,concepts,date,date,false,10,15,16,null, confiWager,prefer);
////
////        quizArrayList.add(quiz);
////        when(quizRepository.findAll()).thenReturn(quizArrayList);
////
////        List<Quiz> quizArrayListTest = quizRepository.findAll();
////        assertEquals(1,quizArrayListTest.size());
////        assertEquals("Java Beginners",quizArrayListTest.get(0).getTitle());
////        assertEquals("101",quizArrayListTest.get(0).getQuizCode());
//
//
//    }
//
//
//}
