package com.ssawallafy.workalone_backend.domain.exercise.dto.response;

import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseInfo;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Kind;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExerciseDetailDto {
    private Long exerciseId;

    private int exerciseSet;
    private int setDetail;
    private Set setType;

    private String title;
    private String subTitle;
    private String basicPose;
    private String movement;
    private String breath;

    public static ExerciseDetailDto of(Exercise exercise, ExerciseInfo exerciseInfo) {
        return ExerciseDetailDto.builder().
                exerciseId(exercise.getId())
                .exerciseSet(exercise.getExerciseSet())
                .setDetail(exercise.getSetDetail())
                .setType(exercise.getSetType())
                .title(exerciseInfo.getTitle())
                .subTitle(exerciseInfo.getSubTitle())
                .basicPose(exerciseInfo.getBasicPose())
                .movement(exerciseInfo.getMovement())
                .breath(exerciseInfo.getBreath())
                .build();
    }
}
