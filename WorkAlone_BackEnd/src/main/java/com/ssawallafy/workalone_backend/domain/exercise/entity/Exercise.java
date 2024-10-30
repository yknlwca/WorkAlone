package com.ssawallafy.workalone_backend.domain.exercise.entity;

import com.ssawallafy.workalone_backend.domain.member.entity.Member;
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
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private int restTime;
    private int exerciseSet;
    private int setDetail;

    @Enumerated(EnumType.STRING)
    private Kind exerciseType;

    @Enumerated(EnumType.STRING)
    private Set setType;

    @ColumnDefault(value = "false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isDeleted;
}
