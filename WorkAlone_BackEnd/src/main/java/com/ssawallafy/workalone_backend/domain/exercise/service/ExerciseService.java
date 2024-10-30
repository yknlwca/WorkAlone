package com.ssawallafy.workalone_backend.domain.exercise.service;

import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDto;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public List<ExerciseDto> getExercises(Long memberId) {
        return Optional.ofNullable(exerciseRepository.findByMemberIdAndDeletedIsFalse(memberId)).orElse(new ArrayList<>())
                .stream().map(ExerciseDto::of).toList();
    }
}
