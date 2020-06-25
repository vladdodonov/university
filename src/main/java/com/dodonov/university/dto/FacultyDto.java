package com.dodonov.university.dto;

import com.dodonov.university.dto.base.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FacultyDto extends BaseDto {
    @Schema(name = "Название факультета")
    private String name;
    @Schema(name = "Курсы")
    private List<CourseDto> courses;
}
