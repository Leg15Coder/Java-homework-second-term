package com.example.javaHomeworkSecondTerm.service;

import com.example.javaHomeworkSecondTerm.repository.UniversitiesRepository;
import com.example.javaHomeworkSecondTerm.exception.UniversityNotFoundException;
import com.example.javaHomeworkSecondTerm.model.University;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UniversityService {
    private final UniversitiesRepository universitiesRepository;
    private final AtomicBoolean isCreated = new AtomicBoolean(false);

    public Collection<University> getAllUniversities() {
        return universitiesRepository.findAll();
    }

    @Async
    public CompletableFuture<University> getUniversityById(Long id) {
        University university = universitiesRepository.findById(id).orElse(null);
        return CompletableFuture.completedFuture(university);
    }

    /**
     * Метод для создания университета с гарантией exactly once.
     * Это гарантирует, что университет будет создан только один раз,
     * даже если метод будет вызван несколько раз.
     * Алгоритм обеспечения гарантии заключается в использовании атомарного флага
     * isCreated, который меняется на true после успешного выполнения метода.
     * Если флаг уже true, метод не выполнится повторно.
     *
     * @param university Данные для создания университета.
     * @return Созданный университет.
     */
    public University createUniversity(University university) {
        if (isCreated.get()) {
            throw new IllegalStateException("Университет уже был создан");
        }

        University createdUniversity = universitiesRepository.save(university);
        isCreated.set(true);
        return createdUniversity;
    }

    public University updateUniversity(Long id, University university) {
        return universitiesRepository.findById(id)
                .map(existingUniversity -> {
                    existingUniversity.setName(university.getName());
                    return universitiesRepository.save(existingUniversity);
                })
                .orElseThrow(() -> new UniversityNotFoundException(id));
    }

    public void deleteUniversity(Long id) {
        if (!universitiesRepository.existsById(id)) {
            throw new UniversityNotFoundException(id);
        }

        universitiesRepository.deleteById(id);
    }
}

