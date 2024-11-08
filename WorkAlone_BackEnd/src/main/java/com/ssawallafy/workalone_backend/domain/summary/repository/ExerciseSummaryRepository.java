package com.ssawallafy.workalone_backend.domain.summary.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryDetail;
import com.ssawallafy.workalone_backend.domain.summary.entity.ExerciseSummary;

public interface ExerciseSummaryRepository extends JpaRepository<ExerciseSummary, Long> {

	@Query("SELECT new com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryDetail(e.exerciseGroup.id, e.seq, t.title, s.time, s.kcal, s.video_url) "
		+ "FROM ExerciseSummary s JOIN s.exercise e JOIN e.exerciseType t "
		+ "WHERE s.member.id = :memberId AND s.date = :date")
	List<ExerciseSummaryDetail> findAllByDate(long memberId, LocalDate date);
}
