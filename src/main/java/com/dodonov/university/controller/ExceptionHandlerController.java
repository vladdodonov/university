package com.dodonov.university.controller;

import com.dodonov.university.dto.ErrorDto;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@Log4j
public class ExceptionHandlerController {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APP_JSON = "application/json";

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity handleOfferConverterException(EntityNotFoundException ex) {
        return prepareResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity handleAllExceptions(Throwable ex) {
        return prepareResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity prepareResponse(Throwable e, HttpStatus status) {
        log.error(String.format("Status: %s", status), e);

        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, APP_JSON);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());
        return new ResponseEntity(errorDto, headers, status);
    }
}
