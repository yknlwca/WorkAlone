package com.ssawallafy.workalone_backend.domain.summary.service;

import org.springframework.stereotype.Service;

import com.ssawallafy.workalone_backend.domain.summary.repository.ExerciseSummaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExerciseSummaryServiceImpl implements ExerciseSummaryService {

	private final ExerciseSummaryRepository exerciseSummaryRepository;

}
