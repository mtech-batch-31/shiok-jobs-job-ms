package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.model.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;


@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = {ResponseStatusException.class})
    protected ResponseEntity<Object> handleResponseStatusExceptions(ResponseStatusException ex, WebRequest request) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        baseResponseDto.setReturnCode(String.valueOf(ex.getStatusCode().value()));
        baseResponseDto.setMessage(ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(baseResponseDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            baseResponseDto.setMessage(errorMessage);
        });

        baseResponseDto.setReturnCode(String.valueOf(ex.getStatusCode().value()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponseDto);
    }
}
