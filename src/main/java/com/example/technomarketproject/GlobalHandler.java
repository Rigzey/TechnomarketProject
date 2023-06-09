package com.example.technomarketproject;

import com.example.technomarketproject.model.DTOs.ErrorDTO;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
public class GlobalHandler {

    @Autowired
    protected Logger logger;

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorizedException(UnauthorizedException e) {
        e.printStackTrace();
        logger.error("A new UnauthorizedException has been detected.");
        return generateErrorDto(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequestException(BadRequestException e) {
        e.printStackTrace();
        logger.error("A new BadRequestException has been detected.");
        return generateErrorDto(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFoundException(FileNotFoundException e) {
        e.printStackTrace();
        logger.error("A new FileNotFoundException has been detected.");
        return generateErrorDto(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        e.printStackTrace();
        logger.error("A new ValidationException has been detected.");
        return generateErrorDto(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleOtherExceptions(Exception e) {
        e.printStackTrace();
        logger.fatal("A new Internal Server Error has been detected.");
        return generateErrorDto("Something went wrong. Do not fire me, please!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ErrorDTO generateErrorDto(Object o, HttpStatus s){
        return ErrorDTO.builder()
                .message(o)
                .status(s.value())
                .time(LocalDateTime.now())
                .build();
    }
}
