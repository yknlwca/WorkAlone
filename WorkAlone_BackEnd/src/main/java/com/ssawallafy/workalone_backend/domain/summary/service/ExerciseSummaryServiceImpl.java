package com.ssawallafy.workalone_backend.domain.summary.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryDetail;
import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryReadReq;
import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryReadRes;
import com.ssawallafy.workalone_backend.domain.summary.entity.ExerciseSummary;
import com.ssawallafy.workalone_backend.domain.summary.repository.ExerciseSummaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExerciseSummaryServiceImpl implements ExerciseSummaryService {

	private final ExerciseSummaryRepository exerciseSummaryRepository;

	@Override
	public ExerciseSummaryReadRes readSummary(long memberId, LocalDate date) {

		List<ExerciseSummaryDetail> summaryRawList = exerciseSummaryRepository.findAllByDate(memberId, date);

		// total 계산
		LocalTime totalTime = LocalTime.of(0, 0, 0);
		int totalKcal = 0;
		for (ExerciseSummaryDetail e : summaryRawList) {
			totalTime = totalTime.plusHours(e.getTime().getHour())
				.plusMinutes(e.getTime().getMinute())
				.plusSeconds(e.getTime().getSecond());
			totalKcal += e.getKcal();
		}

		// groupId 기준으로 묶기
		Map<Long, List<ExerciseSummaryDetail>> groupByExerciseGroupId = summaryRawList.stream()
			.collect(Collectors.groupingBy(ExerciseSummaryDetail::getGroupId));

		// groupId 순으로 정렬된 Map 변환
		Map<Long, List<ExerciseSummaryDetail>> sortedMap = new TreeMap<>(groupByExerciseGroupId);

		// 각 그룹 내에서 seq 순으로 정렬
		List<List<ExerciseSummaryDetail>> totalSummary = sortedMap.values().stream()
			.map(group -> group.stream()
				.sorted(Comparator.comparingInt(ExerciseSummaryDetail::getSeq))
				.collect(Collectors.toList()))
			.collect(Collectors.toList());

		ExerciseSummaryReadRes res = ExerciseSummaryReadRes.builder()
			.totalTime(totalTime)
			.totalKcal(totalKcal)
			.totalSummary(totalSummary)
			.build();

		System.out.println(res);

		return res;
	}
}
