package com.ssawallafy.workalone_backend.domain.exercise.service;

import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDto;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseGroup;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseMapping;
import com.ssawallafy.workalone_backend.domain.exercise.exception.ErrorCode;
import com.ssawallafy.workalone_backend.domain.exercise.exception.ExerciseException;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseGroupRepository;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseMappingRepository;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminExerciseService {

    private final ExerciseGroupRepository exerciseGroupRepository;
    private final ExerciseRepository exerciseRepository;

    @Transactional(readOnly = true)
    public List<ExerciseDto> getExercises(Long organizationId) {

        // Organization Id를 기반으로 통합 운동 테이블에서 조회
        List<ExerciseGroup> exerciseGroups = exerciseGroupRepository.findByOrganizationId(organizationId);

        List<ExerciseDto> exerciseDtos = new ArrayList<>();
        for (ExerciseGroup exerciseGroup : exerciseGroups) {
            // 각 통합운동 ID에 속한 개별 운동을 가져온다.
            List<Exercise> exercises = exerciseRepository.findByExerciseGroupAndDeletedIsFalse(exerciseGroup);
            // 통합 운동인 경우와 개별 운동인 경우를 나눠서 반환한다.
            if (exercises.size() > 1) {
                // 통합 운동인 경우
                exerciseDtos.add(ExerciseDto.ofIntegrated(exerciseGroup));
            } else {
                // 개별 운동인 경우
                exerciseDtos.add(ExerciseDto.ofIndividual(exercises.get(0), exerciseGroup));
            }
        }
        return exerciseDtos;
    }
}
