package com.ssawallafy.workalone_backend.domain.exercise.repository;

import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByExerciseGroupAndDeletedIsFalse(ExerciseGroup exerciseGroup);

    List<Exercise> findByExerciseGroupIdAndDeletedIsFalseOrderBySeq(Long exerciseGroupId);
}
