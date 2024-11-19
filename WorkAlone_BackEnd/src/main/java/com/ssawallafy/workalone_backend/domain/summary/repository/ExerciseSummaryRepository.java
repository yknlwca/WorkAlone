package com.ssawallafy.workalone_backend.domain.summary.repository;

import java.time.LocalDate;
import java.util.List;

import com.ssawallafy.workalone_backend.domain.admin_members.dto.ReadVideoResDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryDetailEntity;
import com.ssawallafy.workalone_backend.domain.summary.entity.ExerciseSummary;

public interface ExerciseSummaryRepository extends JpaRepository<ExerciseSummary, Long> {

	@Query("SELECT new com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryDetailEntity(e.exerciseGroup.id, e.seq, t.title, e.exerciseSet, e.exerciseRepeat, s.time, s.kcal, s.video_url) "
		+ "FROM ExerciseSummary s JOIN s.exercise e JOIN e.exerciseType t "
		+ "WHERE s.member.id = :memberId AND s.date = :date")
	List<ExerciseSummaryDetailEntity> findAllByDate(long memberId, LocalDate date);

	@Query("SELECT e.date FROM ExerciseSummary e WHERE e.member.id = :memberId")
	List<LocalDate> findAllDateByMemberId(long memberId);

	List<ExerciseSummary> findAllByMemberId(long memberId);

	@Query("SELECT new com.ssawallafy.workalone_backend.domain.admin_members.dto.ReadVideoResDetail("
			+ "s.date, g.title, s.video_url) "
			+ "FROM ExerciseSummary s JOIN s.exercise e JOIN e.exerciseGroup g "
			+ "WHERE s.member.id = :memberId AND s.video_url IS NOT NULL AND e.seq = 1 "
			+ "ORDER BY s.date DESC ")
    List<ReadVideoResDetail> findAllAndTitleByMemberId(Long memberId);
}
