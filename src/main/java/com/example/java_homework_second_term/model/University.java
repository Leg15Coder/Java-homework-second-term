package com.example.java_homework_second_term.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "universities")
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

