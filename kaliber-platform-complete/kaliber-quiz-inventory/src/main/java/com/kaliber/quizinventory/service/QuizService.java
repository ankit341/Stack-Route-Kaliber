
package com.kaliber.quizinventory.service;

import com.kaliber.quizinventory.exception.QuizNotFoundException;
import com.kaliber.quizinventory.model.Quiz;
import com.kaliber.quizinventory.model.ReportQuiz;
import com.kaliber.quizinventory.repository.QuizRepositoryImpl;
import com.kaliber.quizinventory.serviceproxy.QuizServiceProxxy;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Service
public class QuizService implements QuizServiceInterface {

    @Autowired
    private QuizRepositoryImpl quizRepo;

    @Autowired
    QuizServiceProxxy quizServiceProxxy;

    @Value("${jwt.signing.key}")
    String jwtSigningKey;

    //	@Autowired
//private MongoTemplate mongoTemplate;​
    //	@Autowired
//	public QuizService(MongoTemplate mongoTemplate) {
//		this.mongoTemplate = mongoTemplate;
//	}
	/* This method finds all the quizzes present with parameter page and limit
		accesses the repository with the object
	 */
    public List<Quiz> findAll(Integer page, Integer limit) throws QuizNotFoundException {
        if (quizRepo.findAll(page, limit).isEmpty()) {
            throw new QuizNotFoundException("Quiz not found");
        }
        System.out.println("page and limit"+page+limit);
        //return quizRepo.findAll(page,limit).getContent();
        return quizRepo.findAll(page,limit);

    }

    /* This method posts tje quizzes,
        accesses the repository with the object and calls the save function of the repository
     */
    public Quiz save(Quiz quiz) throws QuizNotFoundException {
        System.out.println(quiz);
        UUID quizId = UUID.randomUUID();
        quiz.setQuizId(quizId);
        Quiz savedQuiz = quizRepo.save(quiz);
        //Call the semantic service API to create Quiz node
        //Get the saved Quiz and get the subject and concepts of the quiz and post it to NEO4J if they dont exist?
        String subject = savedQuiz.getSubject();
        if (savedQuiz != null) {
            quizServiceProxxy.createQuizNode(savedQuiz.getQuizCode());
            quizServiceProxxy.createSubjectNode(subject);
            quizServiceProxxy.mapQuizToSubject(savedQuiz.getQuizCode(), subject);
            quizServiceProxxy.quizHasQuestionOn(savedQuiz.getQuizCode(), savedQuiz.getConcepts());

        }
        return savedQuiz;
    }

    /* This method finds the quiz with quizcode present
        accesses the repository with the object and calls the findByCode method
     */
    public Quiz findByQuizCode(String quizCode) throws QuizNotFoundException {
        Quiz someQuiz = quizRepo.findByQuizCode(quizCode);
        System.out.println("SOMEQUIZ IS " + someQuiz);
        return someQuiz;
    }
    //	public ArrayList<Quiz> findByCreatedBy(String createdBy) throws QuizNotFoundException{
//		Query query = new Query();
//		query.addCriteria(Criteria.where("createdBy").is(createdBy));
//		return (ArrayList<Quiz>) mongoTemplate.find(query, Quiz.class);
//	}​
    /* This method modifies the quiz already present according to the quizcode parameter
            accesses the repository with the object
         */

    public Quiz modify(Quiz quiz, String quizCode) throws QuizNotFoundException {
//        quiz.setQuizCode(quizCode);
//        System.out.println("before modify");
        Quiz quiz1 = quizRepo.findByQuizCode(quizCode);
        quiz1.setTitle(quiz.getTitle());
        quiz1.setDescription(quiz.getDescription());
        quiz1.setConcepts(quiz.getConcepts());
        quiz1.setQuizType(quiz.getQuizType());
        quiz1.setMaxScore(quiz.getMaxScore());
        Quiz savedQuiz = quizRepo.save(quiz1);
        return savedQuiz;
    }

    /* This method finds all the quizzes present and gives the count
        accesses the repository with the object
     */
    public int getCountOfQuizzes() {
        return quizRepo.getCountOfQuizzes();
    }

    @Override
    public ArrayList<Quiz> getByQuizList(Integer page, Integer limit, String title, String quizCode, ArrayList<String> concepts, String authoredBy, String subject, Quiz.statusValue statusvalue)
            throws QuizNotFoundException{
        System.out.println("page " + page);
        System.out.println("limit " + limit);
        System.out.println("title is " + title);
        System.out.println("quizCode is " + quizCode);
        System.out.println("concepts is " + concepts);
        System.out.println("authoredBy is " + authoredBy);
        System.out.println("subject is " + subject);
        Query query = new Query();
        if (title != null) {
            query.addCriteria(Criteria.where("title").is(title));
        }
        if (quizCode != null) {
            query.addCriteria(Criteria.where("quizCode").is(quizCode));
        }
        if (concepts != null) {
            query.addCriteria(Criteria.where("concepts").is(concepts));
        }
        if (authoredBy != null) {
            query.addCriteria(Criteria.where("authoredBy").is(authoredBy));
        }
        if (subject != null) {
            query.addCriteria(Criteria.where("subject").is(subject));
        }
        if(statusvalue!=null){
            query.addCriteria(Criteria.where("statusvalue").is(statusvalue));
        }
//		ArrayList<Quiz> result = (ArrayList<Quiz>)quizRepo.findAll();
        ArrayList<Quiz> result = quizRepo.getByQuizList(page,limit,title, quizCode, concepts, authoredBy, subject,statusvalue);
        System.out.println("QUIZ LIST IS" + result);
        return result;
//		return (ArrayList<Quiz>) mongoTemplate.findAll(Quiz.class);
    }

    @Override
    public Quiz changeStatus(String quizCode, Quiz.statusValue statusValue) throws QuizNotFoundException {
        Quiz retrievedQuiz = quizRepo.findByQuizCode(quizCode);
        retrievedQuiz.setStatusvalue(statusValue);
        Quiz modifiedQuiz = quizRepo.save(retrievedQuiz);
        return modifiedQuiz;
    }
    public String loadByUserName(HttpServletRequest httpServletRequest) {
        System.out.println("inside load byt username");
        String username = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if (cookie.getName().equals("JWT_TOKEN")) {
                username = Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(cookie.getValue()).getBody()
                        .get("username", String.class);
            }
        }
        return username;
    }

    @Override
    public Quiz reportQuiz(String quizCode, ReportQuiz report, HttpServletRequest httpServletRequest) throws QuizNotFoundException {
        String username = loadByUserName(httpServletRequest);
        report.setUserName(username);
        Quiz quiz = quizRepo.findByQuizCode(quizCode);
        System.out.println("QUIZ IS " + quiz);
        if(quiz.getReportQuiz().size() == 0) {
            ArrayList<ReportQuiz> report1 = new ArrayList<ReportQuiz>();
            report1.add(report);
            quiz.setReportQuiz(report1);
            Quiz updatedQuiz = quizRepo.save(quiz);
            System.out.println("UPDATEDQUIZ IS " + updatedQuiz);
            return updatedQuiz;
        }
        else {
            ArrayList<ReportQuiz>existingReport = quiz.getReportQuiz();
            existingReport.add(report);
            quiz.setReportQuiz(existingReport);
            Quiz updatedQuiz = quizRepo.save(quiz);
            System.out.println("UPDATEDQUIZ IS " + updatedQuiz);
            return updatedQuiz;
        }


//        ArrayList<ReportQuiz> quizReport = new ArrayList<ReportQuiz>();
////                quiz.getReportQuiz();
//
//        if(quizReport.size() != 0) {
//            quizReport = quiz.getReportQuiz();
//
//
//        }
//        else {
//            quizReport = new ArrayList<ReportQuiz>();
//        }
//        quizReport.add(report);
//        return quizRepo.save(quiz);
    }


    @Override
    public HashMap<String, ArrayList<String>> getQuizFeed(String username, HttpServletRequest httpServletRequest) {
        //Get the peer taken quizzes for the logged in users

        HashMap<String, ArrayList<String>> resultFromSemanticService = new HashMap<String, ArrayList<String>>();

        ArrayList<HashMap<String, String>> result1 = quizServiceProxxy.getSuggestedQuiz(username);
//        quizzesFromSemanticService.addAll(result1);
        System.out.println("RESULT ! IS for feed 1" + result1);
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        result1.forEach((res) -> {
            String quizCode = res.get("quizCode");
            ArrayList<String> users = new ArrayList<>();
            if (map.containsKey(quizCode)) {
                users.addAll(map.get(quizCode));
            }
            users.add(res.get("username"));
            map.put(quizCode, users);
            resultFromSemanticService.putAll(map);
        });
        System.out.println("map of sijdas for feed 1:: --> " + map);
//        return map;

        //Get the quizzes for a logged in user based on topics of interest of the user
        ArrayList<HashMap<String, String>> result2 = quizServiceProxxy.getQuizzesBasedOnSubjects(username);
        System.out.println("RESULT FOR FEED 2 " + result2);
        HashMap<String, ArrayList<String>> map2 = new HashMap<>();
        result2.forEach((res) -> {
            System.out.println("RES FOR FEED 2 :: --> " + res);
            String quizCode = res.get("quizCode");
            System.out.println("QUIZCODE FOR FEED2 IS --- :: --> " + quizCode);
            ArrayList<String> users = new ArrayList<>();
            if (map2.containsKey(quizCode)) {
                users.addAll(map2.get(quizCode));
            }
            users.add(res.get("username"));
            map2.put(quizCode, users);
            resultFromSemanticService.putAll(map2);
        });
        System.out.println("map of sijdas FEED 2:: --> " + map2);


        //Fetching trending quizzes....
        ArrayList<HashMap<String, String>> result3 = quizServiceProxxy.getTrendingQuizzes();
        System.out.println("RESULT FOR FEED 3 " + result3);
        HashMap<String, ArrayList<String>> map3 = new HashMap<>();
        result3.forEach((res) -> {
            System.out.println("RES FOR FEED 3 :: --> " + res);
            String quizCode = res.get("quizCode");
            System.out.println("QUIZCODE FOR FEED 3 IS --- :: --> " + quizCode);
            ArrayList<String> users = new ArrayList<>();
            if (map3.containsKey(quizCode)) {
                users.addAll(map3.get(quizCode));
            }
            users.add(res.get("username"));
            map3.put(quizCode, users);
            resultFromSemanticService.putAll(map3);
        });
        System.out.println("DATA in feed 3 :: ===> " + map3);
        System.out.println("DATA from Sematic service :: ==> "+ resultFromSemanticService);
        return resultFromSemanticService;
    }

}
