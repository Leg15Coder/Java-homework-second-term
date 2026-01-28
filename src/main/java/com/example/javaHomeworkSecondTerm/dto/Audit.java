package com.example.javaHomeworkSecondTerm.dto;

import java.time.Instant;
import java.util.UUID;

public record Audit(UUID userId, String action, Instant time, String message) {}
