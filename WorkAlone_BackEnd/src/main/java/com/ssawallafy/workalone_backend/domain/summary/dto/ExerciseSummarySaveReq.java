package com.ssawallafy.workalone_backend.domain.summary.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;

@Getter
public class ExerciseSummarySaveReq {

	private List<ExerciseSummarySaveDetail> summaryList;

	private String video_url;

}
