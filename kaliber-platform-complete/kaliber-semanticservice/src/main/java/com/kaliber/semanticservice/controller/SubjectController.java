package com.kaliber.semanticservice.controller;

import com.kaliber.semanticservice.model.Subject;
import com.kaliber.semanticservice.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    //Create Subject node
    @PostMapping("/subjects")
    public Subject createSubjectNode(@RequestParam(value = "subject") String subject) {
        return subjectService.createSubjectNode(subject);
    }


//    ResponseEntity<?> createSubjectNode(@RequestParam(value = "subject") String subject) {
//        HashMap<String, Object> response = new HashMap<>();
//        try {
//            subjectService.createSubjectNode(subject);
//            response.put("message", "Successfully created subject: ");
//            response.put("count", 1);
//            response.put("result", "subject added");
//            response.put("statusCode", HttpStatus.OK);
//        } catch (Exception qe) {
//            response.put("message", "Can't create concept: ");
//            response.put("count", 1);
//            response.put("result", qe.getMessage());
//            response.put("statusCode", HttpStatus.OK);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(response, HttpStatus.OK);
//
//    }



}
