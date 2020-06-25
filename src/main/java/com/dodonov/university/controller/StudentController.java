package com.dodonov.university.controller;

import com.dodonov.university.dto.ErrorDto;
import com.dodonov.university.dto.PageDto;
import com.dodonov.university.dto.StudentDto;
import com.dodonov.university.service.StudentService;
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
import static com.dodonov.university.util.constant.PathConst.STUDENT;

@RestController
@RequestMapping(API_V1 + STUDENT)
@Log4j
public class StudentController {
    private final StudentService studentService;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentController(StudentService studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @Operation(
            summary = "Метод для создания студента",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращается созданный объект студента",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = StudentDto.class)
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
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto studentDto) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(studentService.save(studentDto), StudentDto.class));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Метод для обновления студента",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращается обновленный объект студента",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = StudentDto.class)
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
    public ResponseEntity<StudentDto> update(@PathVariable(name = "id") UUID id, @RequestBody StudentDto studentDto) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(studentService.update(id, studentDto), StudentDto.class));
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Метод для поиска студента по id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращается найденный объект студента",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = StudentDto.class)
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
    public ResponseEntity<StudentDto> findById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(studentService.findById(id), StudentDto.class));
    }

    @GetMapping
    @Operation(
            summary = "Метод для поиска всех студентов",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Возвращаются студенты",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = StudentDto.class)
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
    public ResponseEntity<PageDto<StudentDto>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "100") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findAll(page, size));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Метод для удаления студента по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Возвращается статус NO_CONTENT"),
                    @ApiResponse(responseCode = "404",
                            description = "Если не найден студент с указанным ID",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorDto.class)
                                    )
                            })
            }
    )
    public ResponseEntity<HttpStatus> deleteById(@PathVariable(name = "id") UUID id) {
        studentService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
