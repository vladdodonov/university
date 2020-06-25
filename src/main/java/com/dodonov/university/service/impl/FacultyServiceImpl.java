package com.dodonov.university.service.impl;

import com.dodonov.university.domain.Faculty;
import com.dodonov.university.dto.FacultyDto;
import com.dodonov.university.dto.PageDto;
import com.dodonov.university.repository.FacultyRepository;
import com.dodonov.university.service.FacultyService;
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
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository, ModelMapper modelMapper) {
        this.facultyRepository = facultyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Faculty save(FacultyDto facultyDto) {
        Faculty faculty = modelMapper.map(facultyDto, Faculty.class);
        return facultyRepository.save(faculty);
    }

    @Override
    @CachePut(key = "#id", value = "faculty")
    public Faculty update(UUID id, FacultyDto facultyDto) {
        Faculty fromDb = facultyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(facultyDto, fromDb);
        return facultyRepository.save(fromDb);
    }

    @Override
    @Cacheable(key = "#id", value = "faculty")
    public Faculty findFullById(UUID id) {
        return facultyRepository.findFullById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public PageDto<FacultyDto> findAll(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<Faculty> page = facultyRepository.findAll(pageable);
        PageDto<FacultyDto> pageDto = modelMapper.map(page,
                new TypeToken<PageDto<FacultyDto>>() {
                }.getType());
        return pageDto;
    }

    @Override
    @CacheEvict(key = "#id", value = "faculty")
    public void deleteById(UUID id) {
        facultyRepository.deleteById(id);
    }
}
