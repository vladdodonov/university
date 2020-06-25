package com.dodonov.university.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class BaseDto {
    @Schema(name = "ID")
    private UUID id;
}
