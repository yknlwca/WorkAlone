package com.ssawallafy.workalone_backend.domain.summary.service;

import java.time.LocalDate;

import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryReadRes;
import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummarySaveReq;

public interface ExerciseSummaryService {
	ExerciseSummaryReadRes readSummary(long memberId, LocalDate date);

	void saveSummary(long memberId, ExerciseSummarySaveReq exerciseSummarySaveReq);
}
