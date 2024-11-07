package com.ssawallafy.workalone_backend.domain.summary.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryReadReq;
import com.ssawallafy.workalone_backend.domain.summary.dto.ExerciseSummaryReadRes;
import com.ssawallafy.workalone_backend.domain.summary.entity.ExerciseSummary;
import com.ssawallafy.workalone_backend.domain.summary.service.ExerciseSummaryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/summary")
@RequiredArgsConstructor
@Tag(name = "Exercise summary", description = "exercise summary api")
public class ExerciseSummaryController {

	private static final Logger log = LoggerFactory.getLogger(ExerciseSummaryController.class);
	private final ExerciseSummaryService exerciseSummaryService;

	@GetMapping
	@Operation(summary = "운동기록 조회", description = "날짜에 해당하는 회원의 운동 기록을 조회합니다.")
	public ResponseEntity<ExerciseSummaryReadRes> getSummary(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date) {

		long memberId = 1L;

		System.out.println("운동기록 조회 시작");

		ExerciseSummaryReadRes res = exerciseSummaryService.readSummary(memberId, date);

		System.out.println("운동기록 조회 끝");

		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
