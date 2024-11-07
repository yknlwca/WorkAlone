package com.ssawallafy.workalone_backend.domain.summary.service;

import java.time.LocalDate;

import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryReadReq;
import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryReadRes;

public interface ExerciseSummaryService {
	ExerciseSummaryReadRes readSummary(long memberId, LocalDate date);
}
