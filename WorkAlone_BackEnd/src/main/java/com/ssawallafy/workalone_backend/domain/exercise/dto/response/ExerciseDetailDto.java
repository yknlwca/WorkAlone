package com.ssawallafy.workalone_backend.domain.exercise.dto.response;

import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseType;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExerciseDetailDto {
    private Long exerciseId;

    private int exerciseSet;
    private int exerciseRepeat;

    private Integer restBtwSet;

    private Set setType;

    private String title;
    private String subTitle;
    private String basicPose;
    private String movement;
    private String breath;

    public static ExerciseDetailDto of(Exercise exercise, ExerciseType exerciseType) {
        return ExerciseDetailDto.builder().
                exerciseId(exercise.getId())
                .exerciseSet(exercise.getExerciseSet())
                .exerciseSet(exercise.getExerciseSet())
                .exerciseRepeat(exercise.getExerciseRepeat())
                .restBtwSet(exercise.getRestBtwSet())
                .setType(exercise.getSetType())
                .title(exerciseType.getTitle())
                .subTitle(exerciseType.getSubTitle())
                .basicPose(exerciseType.getBasicPose())
                .movement(exerciseType.getMovement())
                .breath(exerciseType.getBreath())
                .build();
    }
}
