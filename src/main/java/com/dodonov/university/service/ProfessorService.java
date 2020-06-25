package com.dodonov.university.service;

import com.dodonov.university.domain.Professor;
import com.dodonov.university.dto.PageDto;
import com.dodonov.university.dto.ProfessorDto;

import java.util.UUID;

public interface ProfessorService {
    Professor save(ProfessorDto professorDto);

    Professor update(UUID id, ProfessorDto professorDto);

    Professor findById(UUID id);

    PageDto<ProfessorDto> findAll(int pageNumber, int size);

    void deleteById(UUID id);
}
