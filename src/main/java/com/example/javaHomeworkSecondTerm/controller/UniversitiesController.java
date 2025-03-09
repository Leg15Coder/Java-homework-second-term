package com.example.javaHomeworkSecondTerm.controller;

import com.example.javaHomeworkSecondTerm.api.UniversityApi;
import com.example.javaHomeworkSecondTerm.model.University;
import com.example.javaHomeworkSecondTerm.service.UniversityService;
import io.github.resilience4j.ratelimiter.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/universities")
@RequiredArgsConstructor
public class UniversitiesController implements UniversityApi {
    private final UniversityService universityService;
    private final RateLimiter rateLimiter = RateLimiter.ofDefaults("universityRateController");

    @Override
    public ResponseEntity<Collection<University>> getAllUniversities() {
        return rateLimiter.executeSupplier(() -> {
            Collection<University> universities = universityService.getAllUniversities();
            return ResponseEntity.ok(universities);
        });
    }

    @Override
    public ResponseEntity<CompletableFuture<University>> getUniversityById(@PathVariable Long id) {
        return rateLimiter.executeSupplier(() -> {
            CompletableFuture<University> university = universityService.getUniversityById(id);
            return ResponseEntity.ok(university);
        });
    }

    @Override
    public ResponseEntity<University> createUniversity(@Valid @RequestBody University university) {
        return rateLimiter.executeSupplier(() -> {
            University createdUniversity = universityService.createUniversity(university);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUniversity);
        });
    }

    @Override
    public ResponseEntity<University> updateUniversity(@PathVariable Long id, @Valid @RequestBody University university) {
        return rateLimiter.executeSupplier(() -> {
            University updatedUniversity = universityService.updateUniversity(id, university);
            return ResponseEntity.ok(updatedUniversity);
        });
    }

    @Override
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        return rateLimiter.executeSupplier(() -> {
            universityService.deleteUniversity(id);
            return ResponseEntity.noContent().build();
        });
    }
}

