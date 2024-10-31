package com.ssawallafy.workalone_backend.domain.exercise.controller;

import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDetailDto;
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

    //TODO: 추후에 RequestHeader 추가 해야함
    @GetMapping("/{exercise_id}")
    ResponseEntity<?> getExercisesDetails(@PathVariable(name = "exercise_id") Long exerciseId, @RequestParam(name = "exercise_type") String exerciseType) {
        //TODO: memberId를 검증하는 로직 필요
        Long memberId = 1L;
        List<ExerciseDetailDto> exerciseDetailDtos = exerciseService.getExercisesDetails(exerciseId, exerciseType);
        return new ResponseEntity<>(exerciseDetailDtos, HttpStatus.OK);
    }
}
