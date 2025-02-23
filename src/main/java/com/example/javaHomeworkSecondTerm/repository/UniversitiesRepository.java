package com.example.javaHomeworkSecondTerm.repository;

import com.example.javaHomeworkSecondTerm.model.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversitiesRepository extends JpaRepository<University, Long> { }
