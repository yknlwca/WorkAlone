package com.ssawallafy.workalone_backend.domain.exercise.controller;

import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDto;
import com.ssawallafy.workalone_backend.domain.exercise.service.AdminExerciseService;
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
@RequestMapping("/admin/exercises")
@RequiredArgsConstructor
@Tag(name = "AdminExercise", description = "admin exercise api")
public class AdminExerciseController implements AdminExerciseDocs {
    private final AdminExerciseService adminExerciseService;

    @GetMapping("/{organization_id}")
    public ResponseEntity<List<ExerciseDto>> getExercises(@PathVariable("organization_id") Long organizationId) {
        Long memberId = 2L;
        //TODO: 해당 memberId가 Organization의 소유주가 맞는지 확인하는 작업 필요
        //TODO: admin 유저의 요청이 맞는지 확인하는 로직도 필요할 듯
        List<ExerciseDto> exerciseDto = adminExerciseService.getExercises(organizationId);
        return new ResponseEntity<>(exerciseDto, HttpStatus.OK);
    }
}
