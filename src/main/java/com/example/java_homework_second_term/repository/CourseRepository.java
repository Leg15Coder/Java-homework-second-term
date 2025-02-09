package com.example.java_homework_second_term.repository;

import com.example.java_homework_second_term.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> { }
