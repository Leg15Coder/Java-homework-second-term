package com.example.javaHomeworkSecondTerm.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "outbox")
public class OutboxRecord {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "data", nullable = false)
  private String data;

  public OutboxRecord(String data) {
    this.data = data;
  }
}
