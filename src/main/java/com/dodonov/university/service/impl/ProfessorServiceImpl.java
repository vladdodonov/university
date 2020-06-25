package com.dodonov.university.service.impl;

import com.dodonov.university.domain.Professor;
import com.dodonov.university.dto.PageDto;
import com.dodonov.university.dto.ProfessorDto;
import com.dodonov.university.repository.ProfessorRepository;
import com.dodonov.university.service.ProfessorService;
import lombok.extern.log4j.Log4j;
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
@Log4j
public class ProfessorServiceImpl implements ProfessorService {
    private final ProfessorRepository professorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProfessorServiceImpl(ProfessorRepository professorRepository, ModelMapper modelMapper) {
        this.professorRepository = professorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Professor save(ProfessorDto professorDto) {
        Professor professor = modelMapper.map(professorDto, Professor.class);
        return professorRepository.save(professor);
    }

    @Override
    @CachePut(key = "#id", value = "professor")
    public Professor update(UUID id, ProfessorDto professorDto) {
        Professor fromDb = professorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(professorDto, fromDb);
        return professorRepository.save(fromDb);
    }

    @Override
    @Cacheable(key = "#id", value = "professor")
    public Professor findById(UUID id) {
        return professorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public PageDto<ProfessorDto> findAll(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<Professor> page = professorRepository.findAll(pageable);
        PageDto<ProfessorDto> pageDto = modelMapper.map(page,
                new TypeToken<PageDto<ProfessorDto>>() {
                }.getType());
        return pageDto;
    }

    @Override
    @CacheEvict(key = "#id", value = "professor")
    public void deleteById(UUID id) {
        professorRepository.deleteById(id);
    }
}
