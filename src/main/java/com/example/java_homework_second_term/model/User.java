package com.example.java_homework_second_term.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Email не может быть пустым")
    @Email(message = "Неверный формат email")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull(message = "Имя не может быть пустым")
    @Size(min = 1, message = "Имя должно содержать хотя бы 1 символ")
    @Column(name = "name")
    private String name;

    @Size(min = 1, message = "Фамилия должна содержать хотя бы 1 символ")
    @Column(name = "surname")
    private String surname;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
