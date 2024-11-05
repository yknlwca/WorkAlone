package com.ssawallafy.workalone_backend.domain.exercise.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
}
