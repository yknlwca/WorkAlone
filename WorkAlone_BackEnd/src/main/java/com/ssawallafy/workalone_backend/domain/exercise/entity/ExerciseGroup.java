package com.ssawallafy.workalone_backend.domain.exercise.entity;

import com.ssawallafy.workalone_backend.domain.exercise.dto.request.ExerciseReqDto;
import com.ssawallafy.workalone_backend.domain.organization.entity.Organization;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    private String title;

    private Integer restBtwExercise;

    @Builder
    public ExerciseGroup(String title, Integer restBtwExercise, Organization organization) {
        this.title = title;
        this.restBtwExercise = restBtwExercise;
        this.organization = organization;
    }

    public static ExerciseGroup of(Organization organization, ExerciseReqDto exerciseReqDto) {
        return ExerciseGroup.builder()
                .title(exerciseReqDto.getTitle())
                .organization(organization)
                .restBtwExercise((exerciseReqDto.getExerciseDetailReqDtos().size() > 1) ? exerciseReqDto.getRestBtwExercise() : null)
                .build();
    }
}
