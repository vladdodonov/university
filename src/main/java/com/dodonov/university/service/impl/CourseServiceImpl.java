package com.dodonov.university.service.impl;

import com.dodonov.university.domain.Course;
import com.dodonov.university.dto.CourseDto;
import com.dodonov.university.dto.PageDto;
import com.dodonov.university.repository.CourseRepository;
import com.dodonov.university.service.CourseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Course save(CourseDto courseDto) {
        Course course = modelMapper.map(courseDto, Course.class);
        return courseRepository.save(course);
    }

    @Override
    @CachePut(key = "#id", value = "course")
    public Course update(UUID id, CourseDto courseDto) {
        Course fromDb = courseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(courseDto, fromDb);
        return courseRepository.save(fromDb);
    }

    @Override
    @Cacheable(key = "#id", value = "course")
    public Course findFullById(UUID id) {
        return courseRepository.findFullById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public PageDto<CourseDto> findAll(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<Course> page = courseRepository.findAll(pageable);
        PageDto<CourseDto> pageDto = modelMapper.map(page,
                new TypeToken<PageDto<CourseDto>>() {
                }.getType());
        return pageDto;
    }

    @Override
    @CacheEvict(key = "#id", value = "course")
    public void deleteById(UUID id) {
        courseRepository.deleteById(id);
    }
}
