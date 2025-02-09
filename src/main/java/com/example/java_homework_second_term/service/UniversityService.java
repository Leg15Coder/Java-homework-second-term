package com.example.java_homework_second_term.service;

import com.example.java_homework_second_term.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UniversityService {
    private static final Logger log = LoggerFactory.getLogger(UniversityService.class);

    private final UniversityRepository universityRepository;

    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public void logCall() {
        log.info("Вызван метод UniversityService");
    }
}

