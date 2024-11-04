package com.ssawallafy.workalone_backend.domain.summary.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssawallafy.workalone_backend.domain.summary.service.ExerciseSummaryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/summary")
@RequiredArgsConstructor
@Tag(name = "Exercise summary", description = "exercise summary api")
public class ExerciseSummaryController {

	private final ExerciseSummaryService exerciseSummaryService;
}
