package com.example.javaHomeworkSecondTerm.repository;

import com.example.javaHomeworkSecondTerm.model.OutboxRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<OutboxRecord, Long> { }
