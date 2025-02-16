package com.example.java_homework_second_term.repository;

import com.example.java_homework_second_term.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { }
