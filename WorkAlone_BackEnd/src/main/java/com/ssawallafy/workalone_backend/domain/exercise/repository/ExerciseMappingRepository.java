package com.ssawallafy.workalone_backend.domain.exercise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseMapping;

public interface ExerciseMappingRepository extends JpaRepository<ExerciseMapping, Long> {

	List<ExerciseMapping> findByMemberId(Long memberId);
}
