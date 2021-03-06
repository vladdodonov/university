package com.dodonov.university.controller;

import com.dodonov.university.dto.CourseDto;
import com.dodonov.university.dto.ErrorDto;
import com.dodonov.university.dto.PageDto;
import com.dodonov.university.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import static com.dodonov.university.util.constant.PathConst.COURSE;

@RestController
@RequestMapping(API_V1 + COURSE)
public class CourseController {
    private final CourseService courseService;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseController(CourseService courseService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @Operation(
            summary = "Метод для создания курса",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращается созданный объект курса",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CourseDto.class)
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
    public ResponseEntity<CourseDto> create(@RequestBody CourseDto courseDto) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(courseService.save(courseDto), CourseDto.class));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Метод для обновления курса",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращается обновленный объект курса",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CourseDto.class)
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
    public ResponseEntity<CourseDto> update(@PathVariable(name = "id") UUID id, @RequestBody CourseDto courseDto) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(courseService.update(id, courseDto), CourseDto.class));
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Метод для поиска курса по id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращается найденный объект курса",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CourseDto.class)
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
    public ResponseEntity<CourseDto> findById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(courseService.findFullById(id), CourseDto.class));
    }

    @GetMapping
    @Operation(
            summary = "Метод для поиска всех курсов",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращаются курсы",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CourseDto.class)
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
    public ResponseEntity<PageDto<CourseDto>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "100") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(page, size));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Метод для удаления курса по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Возвращается статус NO_CONTENT"),
                    @ApiResponse(responseCode = "404",
                            description = "Если не найден курс с указанным ID",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorDto.class)
                                    )
                            })
            }
    )
    public ResponseEntity<HttpStatus> deleteById(@PathVariable(name = "id") UUID id) {
        courseService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}