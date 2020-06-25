package com.dodonov.university.config;

import com.dodonov.university.domain.Course;
import com.dodonov.university.domain.Faculty;
import com.dodonov.university.domain.Professor;
import com.dodonov.university.domain.Student;
import com.dodonov.university.dto.CourseDto;
import com.dodonov.university.dto.FacultyDto;
import com.dodonov.university.dto.ProfessorDto;
import com.dodonov.university.dto.StudentDto;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<ProfessorDto, Professor> professorDtoToProfessorConverter = context -> {
            if (context.getSource() != null) {
                Professor professor = new Professor();
                modelMapper.map(context.getSource(), professor);
                return professor;
            }
            return null;
        };

        Converter<Professor, ProfessorDto> professorToProfessorDtoConverter = context -> {
            if (context.getSource() != null) {
                ProfessorDto professorDto = new ProfessorDto();
                modelMapper.map(context.getSource(), professorDto);
                return professorDto;
            }
            return null;
        };

        Converter<FacultyDto, Faculty> facultyDtoToFacultyConverter = context -> {
            if (context.getSource() != null) {
                Faculty faculty = new Faculty();
                modelMapper.map(context.getSource(), faculty);
                return faculty;
            }
            return null;
        };

        Converter<Faculty, FacultyDto> facultyToFacultyDtoConverter = context -> {
            if (context.getSource() != null) {
                FacultyDto facultyDto = new FacultyDto();
                modelMapper.map(context.getSource(), facultyDto);
                return facultyDto;
            }
            return null;
        };

        Converter<List<StudentDto>, List<Student>> listStudentDtoToListStudentConverter = context -> {
            List<Student> students = null;
            List<StudentDto> studentDtos = context.getSource();
            if (context.getSource() != null && !context.getSource().isEmpty()) {
                students = new ArrayList<>();
                for (StudentDto dto : studentDtos) {
                    Student student = modelMapper.map(dto, Student.class);
                    students.add(student);
                }
            }
            if (students == null || students.isEmpty()) {
                return Collections.emptyList();
            } else {
                return students;
            }
        };

        Converter<List<Student>, List<StudentDto>> listStudentToListStudentDtoConverter = context -> {
            List<Student> students = context.getSource();
            List<StudentDto> studentDtos = null;
            if (context.getSource() != null && !context.getSource().isEmpty()) {
                studentDtos = new ArrayList<>();
                for (Student student : students) {
                    StudentDto studentDto = modelMapper.map(student, StudentDto.class);
                    studentDtos.add(studentDto);
                }
            }
            if (studentDtos == null || studentDtos.isEmpty()) {
                return Collections.emptyList();
            } else {
                return studentDtos;
            }
        };

        Condition<?, ?> isFieldNull = (Condition<Object, Object>) context -> context.getSource() == null;

        PropertyMap<CourseDto, Course> dtoToCourseMap = new PropertyMap<CourseDto, Course>() {
            @Override
            protected void configure() {
                using(professorDtoToProfessorConverter).map(source.getProfessor(), destination.getProfessor());
                using(facultyDtoToFacultyConverter).map(source.getFaculty(), destination.getFaculty());
                using(listStudentDtoToListStudentConverter).map(source.getStudents(), destination.getStudents());
                when(isFieldNull).skip(source.getId(), destination.getId());
            }
        };

        PropertyMap<Course, CourseDto> courseToCourseDtoMap = new PropertyMap<Course, CourseDto>() {
            @Override
            protected void configure() {
                using(professorToProfessorDtoConverter).map(source.getProfessor(), destination.getProfessor());
                using(facultyToFacultyDtoConverter).map(source.getFaculty(), destination.getFaculty());
                using(listStudentToListStudentDtoConverter).map(source.getStudents(), destination.getStudents());
            }
        };

        modelMapper.addMappings(dtoToCourseMap);
        modelMapper.addMappings(courseToCourseDtoMap);

        return modelMapper;
    }
}
