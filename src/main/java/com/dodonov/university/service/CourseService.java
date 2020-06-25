package com.dodonov.university.service;

import com.dodonov.university.domain.Course;
import com.dodonov.university.dto.CourseDto;
import com.dodonov.university.dto.PageDto;

import java.util.UUID;

public interface CourseService {
    Course save(CourseDto courseDto);

    Course update(UUID id, CourseDto courseDto);

    Course findFullById(UUID id);

    PageDto<CourseDto> findAll(int pageNumber, int size);

    void deleteById(UUID id);
}
