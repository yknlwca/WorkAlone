package com.ssawallafy.workalone_backend.domain.exercise.service;

import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDetailDto;
import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDto;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseInfo;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseOrder;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Kind;
import com.ssawallafy.workalone_backend.domain.exercise.exception.ErrorCode;
import com.ssawallafy.workalone_backend.domain.exercise.exception.ExerciseException;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseInfoRepository;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseOrderRepository;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseOrderRepository exerciseOrderRepository;
    private final ExerciseInfoRepository exerciseInfoRepository;

    public List<ExerciseDto> getExercises(Long memberId) {
        List<Exercise> exercises = exerciseRepository.findByMemberIdAndDeletedIsFalse(memberId);
        return exercises.isEmpty() ? new ArrayList<>() : exercises.stream().map(ExerciseDto::of).toList();
    }

    public List<ExerciseDetailDto> getExercisesDetails(Long exerciseId, String exerciseType) {
        Kind kind;
        try {
            kind = Kind.valueOf(exerciseType.toUpperCase());
        } catch (Exception e) {
            throw new ExerciseException(ErrorCode.EXERCISE_TYPE_NOT_CORRECT);
        }
        // 개별형 운동 정보 반환
        if(kind != Kind.INTEGRATED) {
            ExerciseInfo exerciseInfo = exerciseInfoRepository.findByExerciseType(kind);
            Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new ExerciseException(ErrorCode.EXERCISE_NOT_FOUND));
            return new ArrayList<>(Collections.singletonList(ExerciseDetailDto.of(exercise, exerciseInfo)));
        }

        // 통합형 운동 정보 반환
        List<Long> orders = exerciseOrderRepository.findByIntegratedExerciseIdOrderByOrderPosition(exerciseId).stream().map(ExerciseOrder::getIndividualExercise).toList();
        List<ExerciseDetailDto> result = new ArrayList<>(orders.size());
        for (Long order : orders) {
            Exercise exercise = exerciseRepository.findById(order).orElseThrow(() -> new ExerciseException(ErrorCode.EXERCISE_NOT_FOUND));
            ExerciseInfo exerciseInfo = Optional.ofNullable(exerciseInfoRepository.findByExerciseType(exercise.getExerciseType())).orElseThrow(() -> new ExerciseException(ErrorCode.EXERCISE_TYPE_NOT_CORRECT));
            result.add(ExerciseDetailDto.of(exercise, exerciseInfo));
        }
        return result;
    }
}
