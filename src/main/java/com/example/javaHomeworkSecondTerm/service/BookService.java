package com.example.javaHomeworkSecondTerm.service;

import com.example.javaHomeworkSecondTerm.repository.BooksRepository;
import com.example.javaHomeworkSecondTerm.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service
// @RequiredArgsConstructor
public class BookService {
    private final BooksRepository booksRepository;

    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public Collection<Book> getAllBooks() {
        return Collections.emptyList();
    }

    public Book createBook(Book book) {
        return new Book();
    }
}

