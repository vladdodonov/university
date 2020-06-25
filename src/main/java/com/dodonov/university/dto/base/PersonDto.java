package com.dodonov.university.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonDto extends BaseDto {
    @Schema(name = "Имя")
    private String firstName;
    @Schema(name = "Фамилия")
    private String lastName;
}
