package com.example.javaHomeworkSecondTerm.controller;

import com.example.javaHomeworkSecondTerm.model.Course;
import com.example.javaHomeworkSecondTerm.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CoursesController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<Collection<Course>> getAllCourses() {
        final Collection<Course> result = courseService.getAllCourses();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
        final Course result = courseService.createCourse(course);
        log.info("created %s".formatted(result.toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}

