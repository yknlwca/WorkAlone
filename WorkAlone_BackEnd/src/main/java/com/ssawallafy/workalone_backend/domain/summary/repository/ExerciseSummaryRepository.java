package com.ssawallafy.workalone_backend.domain.summary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssawallafy.workalone_backend.domain.summary.entity.ExerciseSummary;

public interface ExerciseSummaryRepository extends JpaRepository<ExerciseSummary, Long> {
}
