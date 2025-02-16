package com.example.java_homework_second_term.service;

import com.example.java_homework_second_term.model.University;
import com.example.java_homework_second_term.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service
// @RequiredArgsConstructor
public class UniversityService {
    private final UniversityRepository universityRepository;

    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public Collection<University> getAllUniversities() {
        return Collections.emptyList();
    }

    public University createUniversity(University university) {
        return new University();
    }
}

