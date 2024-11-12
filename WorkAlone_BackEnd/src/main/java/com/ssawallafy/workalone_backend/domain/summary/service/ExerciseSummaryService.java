package com.ssawallafy.workalone_backend.domain.summary.service;

import java.time.LocalDate;
import java.util.List;

import com.amazonaws.HttpMethod;
import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryReadRes;
import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummarySaveReq;
import com.ssawallafy.workalone_backend.domain.summary.dto.UrlResponse;

public interface ExerciseSummaryService {

	UrlResponse generatePreSignUrl(String s, String bucketName, HttpMethod httpMethod);

	ExerciseSummaryReadRes readSummary(long memberId, LocalDate date);

	void saveSummary(long memberId, ExerciseSummarySaveReq exerciseSummarySaveReq);

	List<LocalDate> readDateList(long memberId);
}
