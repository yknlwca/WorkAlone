package com.ssawallafy.workalone_backend.global.error;

import com.ssawallafy.workalone_backend.domain.exercise.exception.ExerciseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ExerciseException.class)
    public ResponseEntity<String> handleExerciseException(ExerciseException ex) {
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(ex.getMessage());
    }
}
