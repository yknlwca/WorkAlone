package com.ssawallafy.workalone_backend.domain.exercise.dto.response;

import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseGroup;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Kind;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExerciseDto {

    private Long groupId;
    private String title;
    private Integer exerciseSet;
    private Integer exerciseRepeat;
    private Integer restBtwExercise;
    private Kind kind;
    private Set setType;

    public static ExerciseDto ofIndividual(Exercise exercise, ExerciseGroup exerciseGroup) {
        return ExerciseDto.builder().
                title(exerciseGroup.getTitle())
                .groupId(exerciseGroup.getId())
                .restBtwExercise(null)
                .exerciseSet(exercise.getExerciseSet())
                .exerciseRepeat(exercise.getExerciseRepeat())
                .setType(exercise.getSetType())
                .kind(Kind.INDIVIDUAL)
                .build();
    }

    public static ExerciseDto ofIntegrated(ExerciseGroup exerciseGroup) {
        return ExerciseDto.builder().
                title(exerciseGroup.getTitle())
                .groupId(exerciseGroup.getId())
                .restBtwExercise(exerciseGroup.getRestBtwExercise())
                .exerciseSet(null)
                .exerciseRepeat(null)
                .setType(null)
                .kind(Kind.INTEGRATED)
                .build();
    }
}
