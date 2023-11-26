package com.bikkadit.electronic.store.exceptions;

import com.bikkadit.electronic.store.helper.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFondException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFondException exception){
        logger.info("Entering ResourceNotFondException Handler...");
        ApiResponse response = ApiResponse.builder().message(exception.getMessage()).success(false).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        logger.info("Entering MethodArgumentNotValidException Handler...");
        Map<String,Object> response = new HashMap<>();
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        allErrors.stream().forEach(error-> {
                    String message = error.getDefaultMessage();
                    String field = ((FieldError) error).getField();
                    response.put(field,message);
                });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
