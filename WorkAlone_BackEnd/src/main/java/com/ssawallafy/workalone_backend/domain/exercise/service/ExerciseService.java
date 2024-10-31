package com.ssawallafy.workalone_backend.domain.exercise.service;

import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDto;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public List<ExerciseDto> getExercises(Long memberId) {
        List<Exercise> exercises = exerciseRepository.findByMemberIdAndDeletedIsFalse(memberId);
        return exercises.isEmpty() ? new ArrayList<>() : exercises.stream().map(ExerciseDto::of).toList();
    }
}
