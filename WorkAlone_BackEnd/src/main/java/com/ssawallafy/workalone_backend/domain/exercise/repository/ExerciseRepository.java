package com.ssawallafy.workalone_backend.domain.exercise.repository;

import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    public List<Exercise> findByMemberIdAndDeletedIsFalse(Long memberId);
}
