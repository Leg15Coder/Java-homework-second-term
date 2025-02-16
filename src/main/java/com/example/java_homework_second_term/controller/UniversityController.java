package com.example.java_homework_second_term.controller;

import com.example.java_homework_second_term.model.University;
import com.example.java_homework_second_term.service.UniversityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/universities")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService universityService;

    @GetMapping
    public ResponseEntity<Collection<University>> getAllUniversities() {
        final Collection<University> result = universityService.getAllUniversities();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<University> createUniversity(@Valid @RequestBody University university) {
        final University result = universityService.createUniversity(university);
        log.info("created %s".formatted(result.toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}

