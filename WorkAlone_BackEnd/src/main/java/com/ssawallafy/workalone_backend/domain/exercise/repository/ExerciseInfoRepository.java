package com.ssawallafy.workalone_backend.domain.exercise.repository;

import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseInfo;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Kind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseInfoRepository extends JpaRepository<ExerciseInfo, Long> {
    ExerciseInfo findByExerciseType(Kind exerciseType);
}
