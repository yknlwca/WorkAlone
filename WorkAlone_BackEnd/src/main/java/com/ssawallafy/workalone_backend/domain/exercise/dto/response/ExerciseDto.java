package com.ssawallafy.workalone_backend.domain.exercise.dto.response;

import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Kind;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExerciseDto {

    private Long exerciseId;
    private String title;
    private int restTime;
    private Integer exerciseSet;
    private Integer setDetail;
    private Kind exerciseType;
    private Set setType;

    public static ExerciseDto of(Exercise exercise) {
        return ExerciseDto.builder().
                exerciseId(exercise.getId()).title(exercise.getTitle())
                .restTime(exercise.getRestTime()).exerciseSet(exercise.getExerciseSet())
                .setDetail(exercise.getSetDetail()).exerciseType(exercise.getExerciseType())
                .setType(exercise.getSetType())
                .build();
    }
}
