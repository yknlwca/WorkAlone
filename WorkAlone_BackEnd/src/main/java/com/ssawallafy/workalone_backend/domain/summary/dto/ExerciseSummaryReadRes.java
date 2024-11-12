package com.ssawallafy.workalone_backend.domain.summary.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ExerciseSummaryReadRes {

	private int totalTime;

	private int totalKcal;

	private List<List<ExerciseSummaryDetail>> totalSummary;

}
