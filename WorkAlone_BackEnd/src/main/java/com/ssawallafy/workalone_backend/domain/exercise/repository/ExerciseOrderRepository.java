package com.ssawallafy.workalone_backend.domain.exercise.repository;

import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseOrderRepository extends JpaRepository<ExerciseOrder, Long> {
    List<ExerciseOrder> findByIntegratedExerciseIdOrderByOrderPosition(Long integratedExerciseId);
}
