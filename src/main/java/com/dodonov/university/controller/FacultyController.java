package com.dodonov.university.controller;

import com.dodonov.university.dto.ErrorDto;
import com.dodonov.university.dto.FacultyDto;
import com.dodonov.university.dto.PageDto;
import com.dodonov.university.service.FacultyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
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
import static com.dodonov.university.util.constant.PathConst.FACULTY;

@RestController
@RequestMapping(API_V1 + FACULTY)
@Log4j
public class FacultyController {
    private final FacultyService facultyService;
    private final ModelMapper modelMapper;

    public FacultyController(FacultyService facultyService, ModelMapper modelMapper) {
        this.facultyService = facultyService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @Operation(
            summary = "Метод для создания факультета",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращается созданный объект факультета",
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
    public ResponseEntity<FacultyDto> create(@RequestBody FacultyDto facultyDto) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(facultyService.save(facultyDto), FacultyDto.class));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Метод для обновления факультета",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращается обновленный объект факультета",
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
    public ResponseEntity<FacultyDto> update(@PathVariable(name = "id") UUID id, @RequestBody FacultyDto facultyDto) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(facultyService.update(id, facultyDto), FacultyDto.class));
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Метод для поиска факультета по id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращается найденный объект факультета",
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
    public ResponseEntity<FacultyDto> findFullById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(facultyService.findFullById(id), FacultyDto.class));
    }

    @GetMapping
    @Operation(
            summary = "Метод для поиска всех факультетов",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращаются факультеты",
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
    public ResponseEntity<PageDto<FacultyDto>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "100") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(facultyService.findAll(page, size));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Метод для удаления факультета по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Возвращается статус NO_CONTENT"),
                    @ApiResponse(responseCode = "404",
                            description = "Если не найден факультет с указанным ID",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorDto.class)
                                    )
                            })
            }
    )
    public ResponseEntity<HttpStatus> deleteById(@PathVariable(name = "id") UUID id) {
        facultyService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
