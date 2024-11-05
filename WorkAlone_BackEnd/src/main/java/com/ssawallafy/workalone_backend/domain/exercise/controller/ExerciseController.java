package com.ssawallafy.workalone_backend.domain.exercise.controller;

import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDetailDto;
import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDto;
import com.ssawallafy.workalone_backend.domain.exercise.service.ExerciseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
@Tag(name = "Exercise", description = "exercise api")
public class ExerciseController implements ExerciseDocs {

    private final ExerciseService exerciseService;

    //TODO: 추후에 RequestHeader 추가 해야함
    @GetMapping
    public ResponseEntity<List<ExerciseDto>> getExercises() {
        Long memberId = 1L;
        List<ExerciseDto> exerciseDto = exerciseService.getExercises(memberId);
        return new ResponseEntity<>(exerciseDto, HttpStatus.OK);
    }

    //TODO: 추후에 RequestHeader 추가 해야함
    @GetMapping("/{group_id}")
    public ResponseEntity<List<ExerciseDetailDto>> getExercisesDetails(@PathVariable(name = "group_id") Long exerciseId) {
        //TODO: memberId를 검증하는 로직 필요
        Long memberId = 1L;
        List<ExerciseDetailDto> exerciseDetailDtos = exerciseService.getExercisesDetails(exerciseId);
        return new ResponseEntity<>(exerciseDetailDtos, HttpStatus.OK);
    }
}
