package com.kaliber.semanticservice.service;

import com.kaliber.semanticservice.model.Subject;
import com.kaliber.semanticservice.repository.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    @Autowired
    SubjectRepo subjectRepo;

    public Subject createSubjectNode(String subject) {
        return subjectRepo.createSubjectNode(subject);
    }
}
