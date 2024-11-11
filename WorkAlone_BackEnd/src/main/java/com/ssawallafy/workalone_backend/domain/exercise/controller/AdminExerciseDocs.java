package com.ssawallafy.workalone_backend.domain.exercise.controller;

import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminExerciseDocs {
    @Operation(summary = "관리자 운동 정보 조회", description = "관리자가 운영하는 챌린지의 운동 정보를 조회합니다.")
    public ResponseEntity<List<ExerciseDto>> getExercises(Long organizationId);
}
