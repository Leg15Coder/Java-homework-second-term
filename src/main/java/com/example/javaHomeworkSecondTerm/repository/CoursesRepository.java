package com.example.javaHomeworkSecondTerm.repository;

import com.example.javaHomeworkSecondTerm.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepository extends JpaRepository<Course, Long> { }
