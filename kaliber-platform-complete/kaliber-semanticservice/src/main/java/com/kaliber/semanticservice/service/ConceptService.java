package com.kaliber.semanticservice.service;

import com.kaliber.semanticservice.model.Concept;
import com.kaliber.semanticservice.repository.ConceptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class ConceptService {

    @Autowired
    ConceptRepo conceptRepo;

    public Concept createConceptNode(String concept) {
        return conceptRepo.createConceptNode(concept);
    }

    public void mapConceptToQuestion(UUID questionId, ArrayList<String> concept, String taxonomy, String difficultyLevel) {
            conceptRepo.mapConceptToQuestion(questionId, concept ,taxonomy,difficultyLevel);
    }

//    public void conceptBelongsToSubject(String[] concept, String subject) {
//        conceptRepo.conceptBelongsToSubject(concept, subject);
//    }
}
