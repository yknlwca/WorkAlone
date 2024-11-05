package com.ssawallafy.workalone_backend.domain.exercise.repository;

import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseTypeRepository extends JpaRepository<ExerciseType, Long> {
}
