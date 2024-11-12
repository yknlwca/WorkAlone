package com.ssawallafy.workalone_backend.domain.exercise.dto.request;


import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseGroup;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseType;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Set;
import com.ssawallafy.workalone_backend.domain.organization.entity.Organization;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExerciseDetailReqDto {
    private int seq;
    private int exerciseSet;
    private int exerciseRepeat;
    private int restBtwSet;
    private long exerciseType; // 어떤 운동인가?
    private String setType; // 시간형, 횟수형
}
