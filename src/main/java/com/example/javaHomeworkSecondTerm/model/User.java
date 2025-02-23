package com.example.javaHomeworkSecondTerm.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

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
}
