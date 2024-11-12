package com.ssawallafy.workalone_backend.domain.summary.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ExerciseSummaryDetail {

	private long groupId;

	private int seq;

	private String exerciseType;

	private int exerciseSet;
	
	private int exerciseRepeat;

	private LocalTime time;

	private int kcal;

	private String videoUrl;

}
