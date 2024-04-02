package com.mtech.sjmsjob.controller;

import com.mtech.sjmsjob.model.BaseResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

class ControllerAdviceTest {

    private ControllerAdvice controllerAdvice;

    @Mock
    private WebRequest webRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controllerAdvice = new ControllerAdvice();
    }

    @Test
    void testHandleResponseStatusExceptions() {
        // Create a ResponseStatusException for testing
        ResponseStatusException ex = new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");

        // Call the method to be tested
        ResponseEntity<Object> responseEntity = controllerAdvice.handleResponseStatusExceptions(ex, webRequest);

        // Assertions to verify the response
        BaseResponseDto baseResponseDto = (BaseResponseDto) responseEntity.getBody();
        assert baseResponseDto != null;
        Assertions.assertEquals(String.valueOf(ex.getStatusCode().value()), baseResponseDto.getReturnCode());
        Assertions.assertEquals(ex.getReason(), baseResponseDto.getMessage());
        Assertions.assertEquals(ex.getStatusCode(), responseEntity.getStatusCode());
    }

    @Test
    void testHandleValidationExceptions() {
        // Create a MethodArgumentNotValidException for testing
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        // Mock behavior of ex.getBindingResult().getAllErrors() to return validation errors
        Mockito.when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(
                new FieldError("objectName", "field", "Field is required")
        ));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException((MethodParameter) null, bindingResult);

        // Call the method to be tested
        ResponseEntity<Object> responseEntity = controllerAdvice.handleValidationExceptions(ex);

        // Assertions to verify the response
        BaseResponseDto baseResponseDto = (BaseResponseDto) responseEntity.getBody();
        assert baseResponseDto != null;
        Assertions.assertEquals(String.valueOf(HttpStatus.BAD_REQUEST.value()), baseResponseDto.getReturnCode());
        Assertions.assertEquals("Field is required", baseResponseDto.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
