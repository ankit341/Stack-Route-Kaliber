package com.kaliber.semanticservice.controller;

import com.kaliber.semanticservice.service.ConceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class ConceptController {

    @Autowired
    ConceptService conceptService;

    @PostMapping("/concept")
    public ResponseEntity<?> createConceptNode(@RequestBody ArrayList<String> concept) {

        HashMap<String, Object> response= new HashMap<>();
        for(int i=0; i< concept.size(); i++){
            try {
                conceptService.createConceptNode(concept.get(i));
                response.put("message","Successfully created concept: ");
                response.put("count", concept.size());
                response.put("result", "concept added");
                response.put("statusCode", HttpStatus.OK);
            }catch (Exception qe) {
                response.put("message", "Can't create concept: ");
                response.put("count", concept.size());
                response.put("result", qe.getMessage());
                response.put("statusCode", HttpStatus.OK);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //mapConceptToQuestion [RELATED_TO]..
    @PostMapping("/map-concept-to-question")
    public void mapConceptToQuestion(@RequestParam UUID questionId,
                                     @RequestBody ArrayList<String> concept,
                                     @RequestParam String taxonomy,
                                     @RequestParam String difficultyLevel ) {
        conceptService.mapConceptToQuestion(questionId, concept, taxonomy, difficultyLevel);
    }

    //Map Concept to Subject [ABOUT]
//    @PostMapping("/map-concept-to-subject")
//    public void conceptBelongsToSubject(@RequestParam String subject,
//                                        @RequestBody String[] concept ) {
//        conceptService.conceptBelongsToSubject(concept, subject);
//    }


      //Create CONCEPT node if the node is not existing
//    @PostMapping("/concept")
//    public Concept createConceptNode(@RequestParam(value = "concept") String concept) {
//        return conceptService.createConceptNode(concept);
//    }

}
