package com.ssawallafy.workalone_backend.domain.exercise.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExerciseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String subTitle;
    private String basicPose;
    private String movement;
    private String breath;

    @Enumerated(EnumType.STRING)
    private Kind exerciseType;
}
