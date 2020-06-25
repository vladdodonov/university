package com.dodonov.university.service;

import com.dodonov.university.domain.Faculty;
import com.dodonov.university.dto.FacultyDto;
import com.dodonov.university.dto.PageDto;

import java.util.UUID;

public interface FacultyService {
    Faculty save(FacultyDto facultyDto);

    Faculty update(UUID id, FacultyDto facultyDto);

    Faculty findFullById(UUID id);

    PageDto<FacultyDto> findAll(int pageNumber, int size);

    void deleteById(UUID id);
}
