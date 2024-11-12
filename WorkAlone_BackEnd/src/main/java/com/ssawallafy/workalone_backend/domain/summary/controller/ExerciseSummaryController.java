package com.ssawallafy.workalone_backend.domain.summary.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummarySaveReq;
import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryReadRes;
import com.ssawallafy.workalone_backend.domain.summary.dto.UrlResponse;
import com.ssawallafy.workalone_backend.domain.summary.service.ExerciseSummaryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/summary")
@RequiredArgsConstructor
@Tag(name = "Exercise summary", description = "exercise summary api")
public class ExerciseSummaryController {

	private static final Logger log = LoggerFactory.getLogger(ExerciseSummaryController.class);
	private final ExerciseSummaryService exerciseSummaryService;

	@Value("${spring.cloud.aws.s3.bucket}")
	private String bucketName;

	@GetMapping("/url")
	@Operation(summary = "Presigned url 요청", description = "S3 업로드를 위한 presigned url과 객체 Url을 요청.\n클라이언트는 presignedUrl에 PUT 요청을 보내 업로드 할 수 있습니다.")
	public ResponseEntity<UrlResponse> generatePresignedUrl() {

		UrlResponse response = exerciseSummaryService.generatePreSignUrl(UUID.randomUUID() + ".mp4", bucketName,
			com.amazonaws.HttpMethod.PUT);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping
	@Operation(summary = "운동기록 저장", description = "입력된 운동 기록을 저장합니다.")
	public ResponseEntity<?> saveExerciseSummary(@RequestBody ExerciseSummarySaveReq exerciseSummarySaveReq) {

		long memberId = 1L;

		exerciseSummaryService.saveSummary(memberId, exerciseSummarySaveReq);

		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "운동기록 조회", description = "날짜에 해당하는 회원의 운동 기록을 조회합니다.")
	public ResponseEntity<ExerciseSummaryReadRes> getSummary(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

		long memberId = 1L;

		ExerciseSummaryReadRes res = exerciseSummaryService.readSummary(memberId, date);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/date-list")
	@Operation(summary = "운동기록 날짜 조회", description = "날짜에 해당하는 회원의 운동 기록을 조회합니다.")
	public ResponseEntity<List<LocalDate>> getSummaryDateList() {

		long memberId = 1L;

		List<LocalDate> dateList = exerciseSummaryService.readDateList(memberId);

		return new ResponseEntity<>(dateList, HttpStatus.OK);
	}
}
