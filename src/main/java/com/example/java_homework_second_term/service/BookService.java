package com.example.java_homework_second_term.service;

import com.example.java_homework_second_term.model.Book;
import com.example.java_homework_second_term.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service
// @RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Collection<Book> getAllBooks() {
        return Collections.emptyList();
    }

    public Book createBook(Book book) {
        return new Book();
    }
}

