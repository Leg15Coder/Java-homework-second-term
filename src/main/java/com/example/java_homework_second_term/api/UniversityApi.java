package com.example.java_homework_second_term.api;

import com.example.java_homework_second_term.model.University;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collection;

public interface UniversityApi {

    @GetMapping
    @Operation(summary = "Получить все университеты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список университетов успешно получен")
    })
    ResponseEntity<Collection<University>> getAllUniversities();

    @GetMapping("/{id}")
    @Operation(summary = "Получить университет по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Университет успешно получен"),
            @ApiResponse(responseCode = "404", description = "Университет не найден")
    })
    ResponseEntity<University> getUniversityById(@PathVariable Long id);

    @PostMapping
    @Operation(summary = "Создать новый университет")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Университет успешно создан")
    })
    ResponseEntity<University> createUniversity(@Valid @RequestBody University university);

    @PutMapping("/{id}")
    @Operation(summary = "Обновить университет")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Университет успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Университет не найден")
    })
    ResponseEntity<University> updateUniversity(@PathVariable Long id, @Valid @RequestBody University university);

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить университет")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Университет успешно удален"),
            @ApiResponse(responseCode = "404", description = "Университет не найден")
    })
    ResponseEntity<Void> deleteUniversity(@PathVariable Long id);
}
