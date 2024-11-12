package com.ssawallafy.workalone_backend.domain.exercise.dto.request;

import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseGroup;
import com.ssawallafy.workalone_backend.domain.organization.entity.Organization;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ExerciseReqDto {
    private String title;
    private List<ExerciseDetailReqDto> exerciseDetailReqDtos;
    private Integer restBtwExercise;
}
