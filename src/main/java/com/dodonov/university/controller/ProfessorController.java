package com.dodonov.university.controller;

import com.dodonov.university.dto.ErrorDto;
import com.dodonov.university.dto.FacultyDto;
import com.dodonov.university.dto.PageDto;
import com.dodonov.university.dto.ProfessorDto;
import com.dodonov.university.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.dodonov.university.util.constant.PathConst.API_V1;
import static com.dodonov.university.util.constant.PathConst.PROFESSOR;

@RestController
@RequestMapping(API_V1 + PROFESSOR)
@Log4j
public class ProfessorController {
    private final ProfessorService professorService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProfessorController(ProfessorService professorService, ModelMapper modelMapper) {
        this.professorService = professorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @Operation(
            summary = "Метод для создания профессора",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращается созданный объект профессора",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = FacultyDto.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "500",
                            description = "В случае ошибки при сохранении",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorDto.class)
                                    )
                            })
            }
    )
    public ResponseEntity<ProfessorDto> create(@RequestBody ProfessorDto professorDto) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(professorService.save(professorDto), ProfessorDto.class));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Метод для обновления профессора",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращается обновленный объект профессора",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = FacultyDto.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "500",
                            description = "В случае ошибки при сохранении",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorDto.class)
                                    )
                            })
            }
    )
    public ResponseEntity<ProfessorDto> update(@PathVariable(name = "id") UUID id, @RequestBody ProfessorDto professorDto) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(professorService.update(id, professorDto), ProfessorDto.class));
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Метод для поиска профессора по id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращается найденный объект профессора",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = FacultyDto.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "500",
                            description = "В случае ошибки",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorDto.class)
                                    )
                            })
            }
    )
    public ResponseEntity<ProfessorDto> findById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(professorService.findById(id), ProfessorDto.class));
    }

    @GetMapping
    @Operation(
            summary = "Метод для поиска всех профессоров",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращаются профессоры",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = FacultyDto.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "500",
                            description = "В случае ошибки",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorDto.class)
                                    )
                            })
            }
    )
    public ResponseEntity<PageDto<ProfessorDto>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "100") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(professorService.findAll(page, size));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Метод для удаления профессора по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Возвращается статус NO_CONTENT"),
                    @ApiResponse(responseCode = "404",
                            description = "Если не найден профессор с указанным ID",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorDto.class)
                                    )
                            })
            }
    )
    public ResponseEntity<HttpStatus> deleteById(@PathVariable(name = "id") UUID id) {
        professorService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
