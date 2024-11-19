package com.ssawallafy.workalone_backend.domain.summary.dto;

import java.time.LocalTime;

import lombok.Getter;

@Getter
public class ExerciseSummarySaveDetail {

	private long exercise_id;

	private String time;

	private int kcal;
}
