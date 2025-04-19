package com.example.javaHomeworkSecondTerm.api;

import com.example.javaHomeworkSecondTerm.model.Book;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Collection;

@Tag(name = "Books", description = "API для работы с книгами пользователей")
public interface BookApi {

    @Operation(summary = "Получить список всех книг")
    @ApiResponse(responseCode = "200", description = "Успешный ответ",
            content = @Content(schema = @Schema(implementation = Book.class)))
    @GetMapping
    ResponseEntity<Collection<Book>> getAllBooks();

    @Operation(summary = "Получить книгу по ID")
    @ApiResponse(responseCode = "200", description = "Успешный ответ",
            content = @Content(schema = @Schema(implementation = Book.class)))
    @ApiResponse(responseCode = "404", description = "Книга не найдена")
    @GetMapping("/{id}")
    ResponseEntity<Book> getBookById(@Parameter(description = "ID книги") @PathVariable Long id);

    @Operation(summary = "Создать новую книгу")
    @ApiResponse(responseCode = "201", description = "Книга создана",
            content = @Content(schema = @Schema(implementation = Book.class)))
    @PostMapping
    ResponseEntity<Book> createBook(@RequestBody Book book);

    @Operation(summary = "Обновить данные книги (замена всех полей)")
    @ApiResponse(responseCode = "200", description = "Книга обновлена",
            content = @Content(schema = @Schema(implementation = Book.class)))
    @PutMapping("/{id}")
    ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book);

    @Operation(summary = "Частично обновить данные книги")
    @ApiResponse(responseCode = "200", description = "Книга обновлена",
            content = @Content(schema = @Schema(implementation = Book.class)))
    @PatchMapping("/{id}")
    ResponseEntity<Book> patchBook(@PathVariable Long id, @RequestBody Book book);

    @Operation(summary = "Удалить книгу по ID")
    @ApiResponse(responseCode = "204", description = "Книга удалена")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteBook(@PathVariable Long id);
}
