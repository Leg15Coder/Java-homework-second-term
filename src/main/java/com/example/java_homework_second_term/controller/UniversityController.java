package com.example.java_homework_second_term.controller;

import com.example.java_homework_second_term.api.UniversityApi;
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
public class UniversityController implements UniversityApi {
    private final UniversityService universityService;

    @Override
    public ResponseEntity<Collection<University>> getAllUniversities() {
        Collection<University> universities = universityService.getAllUniversities();
        return ResponseEntity.ok(universities);
    }

    @Override
    public ResponseEntity<University> getUniversityById(@PathVariable Long id) {
        University university = universityService.getUniversityById(id);
        return ResponseEntity.ok(university);
    }

    @Override
    public ResponseEntity<University> createUniversity(@Valid @RequestBody University university) {
        University createdUniversity = universityService.createUniversity(university);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUniversity);
    }

    @Override
    public ResponseEntity<University> updateUniversity(@PathVariable Long id, @Valid @RequestBody University university) {
        University updatedUniversity = universityService.updateUniversity(id, university);
        return ResponseEntity.ok(updatedUniversity);
    }

    @Override
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        universityService.deleteUniversity(id);
        return ResponseEntity.noContent().build();
    }
}

