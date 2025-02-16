package com.example.java_homework_second_term.repository;

import com.example.java_homework_second_term.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> { }
