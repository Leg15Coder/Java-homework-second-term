package com.example.java_homework_second_term.api;

import com.example.java_homework_second_term.model.Course;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Tag(name = "Courses", description = "API для работы с курсами пользователей")
public interface CourseApi {

    @Operation(summary = "Получить список всех курсов")
    @ApiResponse(responseCode = "200", description = "Успешный ответ",
            content = @Content(schema = @Schema(implementation = Course.class)))
    @GetMapping
    ResponseEntity<Collection<Course>> getAllCourses();

    @Operation(summary = "Получить курс по ID")
    @ApiResponse(responseCode = "200", description = "Успешный ответ",
            content = @Content(schema = @Schema(implementation = Course.class)))
    @ApiResponse(responseCode = "404", description = "Курс не найден")
    @GetMapping("/{id}")
    ResponseEntity<Course> getCourseById(@Parameter(description = "ID курса") @PathVariable Long id);

    @Operation(summary = "Создать новый курс")
    @ApiResponse(responseCode = "201", description = "Курс создан",
            content = @Content(schema = @Schema(implementation = Course.class)))
    @PostMapping
    ResponseEntity<Course> createCourse(@RequestBody Course course);

    @Operation(summary = "Обновить данные курса (замена всех полей)")
    @ApiResponse(responseCode = "200", description = "Курс обновлен",
            content = @Content(schema = @Schema(implementation = Course.class)))
    @PutMapping("/{id}")
    ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course);

    @Operation(summary = "Частично обновить данные курса")
    @ApiResponse(responseCode = "200", description = "Курс обновлен",
            content = @Content(schema = @Schema(implementation = Course.class)))
    @PatchMapping("/{id}")
    ResponseEntity<Course> patchCourse(@PathVariable Long id, @RequestBody Course course);

    @Operation(summary = "Удалить курс по ID")
    @ApiResponse(responseCode = "204", description = "Курс удален")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCourse(@PathVariable Long id);
}
