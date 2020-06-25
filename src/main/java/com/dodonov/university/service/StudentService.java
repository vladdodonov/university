package com.dodonov.university.service;

import com.dodonov.university.domain.Student;
import com.dodonov.university.dto.PageDto;
import com.dodonov.university.dto.StudentDto;

import java.util.UUID;

public interface StudentService {
    Student save(StudentDto studentDto);

    Student update(UUID id, StudentDto studentDto);

    Student findById(UUID id);

    PageDto<StudentDto> findAll(int pageNumber, int size);

    void deleteById(UUID id);
}
