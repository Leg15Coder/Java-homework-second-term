package com.example.java_homework_second_term.api;

import com.example.java_homework_second_term.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Tag(name = "Users", description = "API для работы с пользователями")
public interface UserApi {

    @Operation(summary = "Получить список всех пользователей")
    @ApiResponse(responseCode = "200", description = "Успешный ответ",
            content = @Content(schema = @Schema(implementation = User.class)))
    @GetMapping
    ResponseEntity<Collection<User>> getAllUsers();

    @Operation(summary = "Получить пользователя по ID")
    @ApiResponse(responseCode = "200", description = "Успешный ответ",
            content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@Parameter(description = "ID пользователя") @PathVariable Long id);

    @Operation(summary = "Создать нового пользователя")
    @ApiResponse(responseCode = "201", description = "Пользователь создан",
            content = @Content(schema = @Schema(implementation = User.class)))
    @PostMapping
    ResponseEntity<User> createUser(@RequestBody User user);

    @Operation(summary = "Обновить данные пользователя (замена всех полей)")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлён",
            content = @Content(schema = @Schema(implementation = User.class)))
    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user);

    @Operation(summary = "Частично обновить данные пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлён",
            content = @Content(schema = @Schema(implementation = User.class)))
    @PatchMapping("/{id}")
    ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody User user);

    @Operation(summary = "Удалить пользователя по ID")
    @ApiResponse(responseCode = "204", description = "Пользователь удалён")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Long id);
}
