package com.example.javaHomeworkSecondTerm.service;

import com.example.javaHomeworkSecondTerm.repository.CoursesRepository;
import com.example.javaHomeworkSecondTerm.model.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service
// @RequiredArgsConstructor
public class CourseService {
    private final CoursesRepository coursesRepository;

    public CourseService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    public Collection<Course> getAllCourses() {
        return Collections.emptyList();
    }

    public Course createCourse(Course course) {
        return new Course();
    }
}

