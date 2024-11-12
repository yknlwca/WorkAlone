package com.ssawallafy.workalone_backend.domain.summary.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ExerciseSummaryDetailEntity {
	private long groupId;

	private int seq;

	private String exerciseType;

	private int exerciseSet;

	private int exerciseRepeat;

	private LocalTime time;

	private int kcal;

	private String videoUrl;

	public ExerciseSummaryDetail toExerciseSummaryDetail(){
		// 시간을 초로 바꾸는 로직
		int time  = this.getTime().toSecondOfDay();
		return new ExerciseSummaryDetail(this.getGroupId(), this.getSeq(), this.getExerciseType(), this.getExerciseSet(), this.getExerciseRepeat(), time, this.getKcal(), this.getVideoUrl());
	}
}
