package com.ssawallafy.workalone_backend.domain.exercise.service;

import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDetailDto;
import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDto;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseGroup;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseType;
import com.ssawallafy.workalone_backend.domain.exercise.exception.ErrorCode;
import com.ssawallafy.workalone_backend.domain.exercise.exception.ExerciseException;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseGroupRepository;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseRepository;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseGroupRepository exerciseGroupRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;

    public List<ExerciseDto> getExercises(Long memberId) {
        // 통합형 테이블에서 운동 ID를 가져온다.
        List<ExerciseGroup> exerciseGroups = exerciseGroupRepository.findByMemberId(memberId);

        List<ExerciseDto> exerciseDtos = new ArrayList<>();
        for (ExerciseGroup exerciseGroup : exerciseGroups) {
            // 각 통합운동 ID에 속한 개별 운동을 가져온다.
            List<Exercise> exercises = exerciseRepository.findByExerciseGroupAndDeletedIsFalse(exerciseGroup);
            // 통합 운동인 경우와 개별 운동인 경우를 나눠서 반환한다.
            if (exercises.size() > 1) {
                exerciseDtos.add(ExerciseDto.ofIntegrated(exerciseGroup));
            } else {
                exerciseDtos.add(ExerciseDto.ofIndividual(exercises.get(0), exerciseGroup));
            }
        }
        return exerciseDtos;
    }

    public List<ExerciseDetailDto> getExercisesDetails(Long groupId) {
        // 통합운동 ID에 속한 개별 운동들을 가져온다.
        List<Exercise> exercises = exerciseRepository.findByExerciseGroupIdAndDeletedIsFalseOrderBySeq(groupId);
        if (exercises.isEmpty()) {
            throw new ExerciseException(ErrorCode.EXERCISE_NOT_FOUND);
        }
        List<ExerciseDetailDto> exerciseDetailDtos = new ArrayList<>();
        for (Exercise exercise : exercises) {
            // 각 개별운동에 대한 설명을 가져온다.
            ExerciseType exerciseType = exerciseTypeRepository.findById(exercise.getExerciseType().getId()).orElseThrow(() -> new ExerciseException(ErrorCode.EXERCISE_TYPE_NOT_CORRECT));
            exerciseDetailDtos.add(ExerciseDetailDto.of(exercise, exerciseType));
        }
        return exerciseDetailDtos;
    }
}
