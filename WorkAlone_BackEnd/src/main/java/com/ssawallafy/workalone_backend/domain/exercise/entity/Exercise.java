package com.ssawallafy.workalone_backend.domain.exercise.entity;

import com.ssawallafy.workalone_backend.domain.exercise.dto.request.ExerciseDetailReqDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private ExerciseGroup exerciseGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private ExerciseType exerciseType;

    private int seq;

    private int exerciseSet;
    private int exerciseRepeat;

    private Integer restBtwSet;

    @Enumerated(EnumType.STRING)
    private Set setType;

    @ColumnDefault(value = "false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean deleted;

    @Builder
    public Exercise(ExerciseGroup exerciseGroup, ExerciseType exerciseType, int seq, int exerciseSet, int exerciseRepeat, Set setType, Integer restBtwSet) {
        this.exerciseGroup = exerciseGroup;
        this.exerciseType = exerciseType;
        this.seq = seq;
        this.exerciseSet = exerciseSet;
        this.exerciseRepeat = exerciseRepeat;
        this.setType = setType;
        this.restBtwSet = restBtwSet;
        this.deleted = false;
    }

    public static Exercise of(ExerciseGroup exerciseGroup, ExerciseType exerciseType, ExerciseDetailReqDto exerciseDetailReqDto) {
        return Exercise.builder()
                .exerciseGroup(exerciseGroup)
                .seq(exerciseDetailReqDto.getSeq())
                .exerciseRepeat(exerciseDetailReqDto.getExerciseRepeat())
                .exerciseSet(exerciseDetailReqDto.getExerciseSet())
                .exerciseType(exerciseType)
                .restBtwSet(exerciseDetailReqDto.getRestBtwSet())
                .setType(Set.valueOf(exerciseDetailReqDto.getSetType().toUpperCase()))
                .build();
    }
}
