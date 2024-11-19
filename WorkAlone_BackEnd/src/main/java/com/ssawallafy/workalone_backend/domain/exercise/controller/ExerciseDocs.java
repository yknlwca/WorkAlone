package com.ssawallafy.workalone_backend.domain.exercise.controller;

import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ExerciseDocs {
    @Operation(summary = "운동 정보 조회", description = "운동 정보를 조회합니다.")
    ResponseEntity<List<ExerciseDto>> getExercises();

    @Operation(summary = "운동 상세 정보 조회", description = "운동 상제 정보를 조회합니다.")
    ResponseEntity<?> getExercisesDetails(@PathVariable(name = "group_id") Long exerciseId);
}
