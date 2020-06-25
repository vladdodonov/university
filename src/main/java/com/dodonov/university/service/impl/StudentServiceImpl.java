package com.dodonov.university.service.impl;

import com.dodonov.university.domain.Student;
import com.dodonov.university.dto.PageDto;
import com.dodonov.university.dto.StudentDto;
import com.dodonov.university.repository.StudentRepository;
import com.dodonov.university.service.StudentService;
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
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Student save(StudentDto studentDto) {
        Student student = modelMapper.map(studentDto, Student.class);
        return studentRepository.save(student);
    }

    @Override
    @CachePut(key = "#id", value = "student")
    public Student update(UUID id, StudentDto studentDto) {
        Student fromDb = studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(studentDto, fromDb);
        return studentRepository.save(fromDb);
    }

    @Override
    @Cacheable(key = "#id", value = "student")
    public Student findById(UUID id) {
        return studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public PageDto<StudentDto> findAll(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<Student> page = studentRepository.findAll(pageable);
        PageDto<StudentDto> pageDto = modelMapper.map(page,
                new TypeToken<PageDto<StudentDto>>() {
                }.getType());
        return pageDto;
    }

    @Override
    @CacheEvict(key = "#id", value = "student")
    public void deleteById(UUID id) {
        studentRepository.deleteById(id);
    }
}
