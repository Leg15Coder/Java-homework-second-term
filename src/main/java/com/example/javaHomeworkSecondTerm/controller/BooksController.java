package com.example.javaHomeworkSecondTerm.controller;

import com.example.javaHomeworkSecondTerm.model.Book;
import com.example.javaHomeworkSecondTerm.service.BookService;
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
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {
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

