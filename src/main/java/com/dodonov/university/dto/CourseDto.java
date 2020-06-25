package com.dodonov.university.dto;

import com.dodonov.university.dto.base.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CourseDto extends BaseDto {
    @Schema(name = "Название курса")
    private String name;
    @Schema(name = "Профессор")
    private ProfessorDto professor;
    @Schema(name = "Факультет")
    private FacultyDto faculty;
    @Schema(name = "Студенты")
    private List<StudentDto> students;
}
