package com.dodonov.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ErrorDto {
    @Schema(name = "Сообщение об ошибке")
    private String message;
}
