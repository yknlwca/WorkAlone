package com.ssawallafy.workalone_backend.domain.exercise.controller;

import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDto;
import com.ssawallafy.workalone_backend.domain.exercise.service.ExerciseService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    //TODO: 추후에 RequestHeader 추가 해야함
    @GetMapping
    ResponseEntity<List<ExerciseDto>> getExercises() {
        Long memberId = 1L;
        List<ExerciseDto> exerciseDto = exerciseService.getExercises(memberId);
        return new ResponseEntity<>(exerciseDto, HttpStatus.OK);
    }
}
