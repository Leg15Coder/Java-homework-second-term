package com.example.javaHomeworkSecondTerm.service;

import com.example.javaHomeworkSecondTerm.repository.UniversitiesRepository;
import com.example.javaHomeworkSecondTerm.exception.UniversityNotFoundException;
import com.example.javaHomeworkSecondTerm.model.University;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class UniversityService {
    private final UniversitiesRepository universitiesRepository;

    public Collection<University> getAllUniversities() {
        return universitiesRepository.findAll();
    }

    // Получить университет по ID
    public University getUniversityById(Long id) {
        return universitiesRepository.findById(id)
                .orElseThrow(() -> new UniversityNotFoundException(id));
    }

    public University createUniversity(University university) {
        return universitiesRepository.save(university);
    }

    public University updateUniversity(Long id, University university) {
        return universitiesRepository.findById(id)
                .map(existingUniversity -> {
                    existingUniversity.setName(university.getName());
                    existingUniversity.setUser(university.getUser());
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

