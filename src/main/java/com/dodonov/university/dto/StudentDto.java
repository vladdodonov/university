package com.dodonov.university.dto;

import com.dodonov.university.domain.Course;
import com.dodonov.university.dto.base.PersonDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentDto extends PersonDto {
    @Schema(name = "Курсы")
    private List<Course> courses;
}
