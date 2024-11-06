package com.ssawallafy.workalone_backend.domain.exercise.repository;

import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseGroupRepository extends JpaRepository<ExerciseGroup, Long> {
    List<ExerciseGroup> findByMemberId(Long memberId);
}
