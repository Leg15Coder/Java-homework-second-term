package com.example.java_homework_second_term.service;

import com.example.java_homework_second_term.model.Course;
import com.example.java_homework_second_term.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service
// @RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Collection<Course> getAllCourses() {
        return Collections.emptyList();
    }

    public Course createCourse(Course course) {
        return new Course();
    }
}

