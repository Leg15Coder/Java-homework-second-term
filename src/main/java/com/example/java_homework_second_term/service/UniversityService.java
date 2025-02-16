package com.example.java_homework_second_term.service;

import com.example.java_homework_second_term.exception.UniversityNotFoundException;
import com.example.java_homework_second_term.model.University;
import com.example.java_homework_second_term.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class UniversityService {
    private final UniversityRepository universityRepository;

    public Collection<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    // Получить университет по ID
    public University getUniversityById(Long id) {
        return universityRepository.findById(id)
                .orElseThrow(() -> new UniversityNotFoundException(id));
    }

    public University createUniversity(University university) {
        return universityRepository.save(university);
    }

    public University updateUniversity(Long id, University university) {
        return universityRepository.findById(id)
                .map(existingUniversity -> {
                    existingUniversity.setName(university.getName());
                    existingUniversity.setUser(university.getUser());
                    return universityRepository.save(existingUniversity);
                })
                .orElseThrow(() -> new UniversityNotFoundException(id));
    }

    public void deleteUniversity(Long id) {
        if (!universityRepository.existsById(id)) {
            throw new UniversityNotFoundException(id);
        }

        universityRepository.deleteById(id);
    }
}

