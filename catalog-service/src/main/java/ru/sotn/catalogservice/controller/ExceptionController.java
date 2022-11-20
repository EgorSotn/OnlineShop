package ru.sotn.catalogservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.xml.bind.ValidationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleException(ValidationException e){
        return ResponseEntity.badRequest().body("{\n" +
                "\"message\" : \"" +
                e.getMessage() + "\"\n}");
    }
}
