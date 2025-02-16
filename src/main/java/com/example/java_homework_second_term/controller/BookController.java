package com.example.java_homework_second_term.controller;

import com.example.java_homework_second_term.model.Book;
import com.example.java_homework_second_term.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Collection<Book>> getAllBooks() {
        final Collection<Book> result = bookService.getAllBooks();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        final Book result = bookService.createBook(book);
        log.info("created %s".formatted(result.toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}

