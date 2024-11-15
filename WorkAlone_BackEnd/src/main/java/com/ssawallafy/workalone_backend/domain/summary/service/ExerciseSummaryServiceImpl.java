package com.ssawallafy.workalone_backend.domain.summary.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseRepository;
import com.ssawallafy.workalone_backend.domain.member.entity.Member;
import com.ssawallafy.workalone_backend.domain.member.exception.BusinessLogicException;
import com.ssawallafy.workalone_backend.domain.member.exception.ErrorCode;
import com.ssawallafy.workalone_backend.domain.member.repository.MemberRepository;
import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryDetail;
import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryDetailEntity;
import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryReadRes;
import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummarySaveDetail;
import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummarySaveReq;
import com.ssawallafy.workalone_backend.domain.summary.dto.UrlResponse;
import com.ssawallafy.workalone_backend.domain.summary.entity.ExerciseSummary;
import com.ssawallafy.workalone_backend.domain.summary.repository.ExerciseSummaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExerciseSummaryServiceImpl implements ExerciseSummaryService {

	private final ExerciseSummaryRepository exerciseSummaryRepository;
	private final MemberRepository memberRepository;
	private final ExerciseRepository exerciseRepository;

	@Autowired
	private AmazonS3 amazonS3;

	@Value("${spring.cloud.aws.s3.bucket}")
	private String bucketName;

	public UrlResponse generatePreSignUrl(String filePath,
		String bucketName,
		HttpMethod httpMethod) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 10); //validfy of 10 minutes

		UrlResponse res = new UrlResponse();
		String fullPath = "video/" + filePath;
		res.setPresignedUrl(
			amazonS3.generatePresignedUrl(bucketName, fullPath, calendar.getTime(), httpMethod).toString());
		res.setObjectUrl("https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + fullPath);
		return res;
	}

	@Override
	public void saveSummary(long memberId, ExerciseSummarySaveReq exerciseSummarySaveReq) {

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND));

		// "mm:ss" 형식의 DateTimeFormatter 생성
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

		for (ExerciseSummarySaveDetail e: exerciseSummarySaveReq.getSummaryList()){

			Exercise exercise = exerciseRepository.findById(e.getExercise_id())
				.orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND));

			ExerciseSummary exerciseSummary = ExerciseSummary.builder()
				.member(member)
				.date(LocalDate.now())
				.exercise(exercise)
				.kcal(e.getKcal())
				.time(LocalTime.parse("00:" + e.getTime(), formatter))
				.video_url(exerciseSummarySaveReq.getVideo_url())
			.build();

			exerciseSummaryRepository.save(exerciseSummary);
		}
	}

	@Override
	public List<LocalDate> readDateList(long memberId) {

		List<LocalDate> rawDateList = exerciseSummaryRepository.findAllDateByMemberId(memberId);
		Set<LocalDate> dateSet = new TreeSet<>();
		for (LocalDate d : rawDateList) {
			dateSet.add(d);
		}
		List<LocalDate> dateList = new ArrayList<>(dateSet);

		return dateList;
	}

	@Override
	public ExerciseSummaryReadRes readSummary(long memberId, LocalDate date) {

		List<ExerciseSummaryDetailEntity> summaryEntityList = exerciseSummaryRepository.findAllByDate(memberId, date);

		// total 계산
		int totalTime = 0;
		int totalKcal = 0;
		List<ExerciseSummaryDetail> summaryRawList = new ArrayList<>();
		for (ExerciseSummaryDetailEntity e : summaryEntityList) {
			ExerciseSummaryDetail detail = e.toExerciseSummaryDetail();
			totalTime += detail.getTime();
			totalKcal += e.getKcal();
			summaryRawList.add(detail);
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

		return res;
	}


}
