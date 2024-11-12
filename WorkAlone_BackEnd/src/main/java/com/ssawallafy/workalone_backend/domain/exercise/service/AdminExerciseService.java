package com.ssawallafy.workalone_backend.domain.exercise.service;

import com.ssawallafy.workalone_backend.domain.exercise.dto.request.ExerciseDetailReqDto;
import com.ssawallafy.workalone_backend.domain.exercise.dto.request.ExerciseReqDto;
import com.ssawallafy.workalone_backend.domain.exercise.dto.response.ExerciseDto;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Exercise;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseGroup;
import com.ssawallafy.workalone_backend.domain.exercise.entity.ExerciseType;
import com.ssawallafy.workalone_backend.domain.exercise.entity.Set;
import com.ssawallafy.workalone_backend.domain.exercise.exception.ErrorCode;
import com.ssawallafy.workalone_backend.domain.exercise.exception.ExerciseException;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseGroupRepository;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseRepository;
import com.ssawallafy.workalone_backend.domain.exercise.repository.ExerciseTypeRepository;
import com.ssawallafy.workalone_backend.domain.member.repository.MemberRepository;
import com.ssawallafy.workalone_backend.domain.organization.entity.Organization;
import com.ssawallafy.workalone_backend.domain.organization.repository.OrganizationRepository;
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
    private final MemberRepository memberRepository;
    private final OrganizationRepository organizationRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;

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

    @Transactional
    public void createExercise(Long memberId, Long organizationId, ExerciseReqDto exerciseReqDto) {
        //TODO: throw 설정에 대해 이야기
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid organization ID"));;

        ExerciseGroup exerciseGroup = ExerciseGroup.of(organization, exerciseReqDto);
        exerciseGroupRepository.save(exerciseGroup);


        List<ExerciseDetailReqDto> exerciseDetailReqDtos = exerciseReqDto.getExerciseDetailReqDtos();
        List<Exercise> exercises = new ArrayList<>();

        for (ExerciseDetailReqDto exerciseDetailReqDto : exerciseDetailReqDtos) {
            validateExerciseDetailReqDto(exerciseDetailReqDto);

            ExerciseType exerciseType = exerciseTypeRepository.findById(exerciseDetailReqDto.getExerciseType())
                    .orElseThrow(() -> new ExerciseException(ErrorCode.EXERCISE_NOT_FOUND));

            exercises.add(Exercise.of(exerciseGroup, exerciseType, exerciseDetailReqDto));
        }

        exerciseRepository.saveAll(exercises);
    }

    private void validateExerciseDetailReqDto(ExerciseDetailReqDto exerciseDetailReqDto) {
        validateSetType(exerciseDetailReqDto.getSetType());
    }

    private void validateSetType(String setType) {
        if (setType == null || setType.isEmpty()) {
            throw new ExerciseException(ErrorCode.EMPTY_SET_TYPE);
        }

        if (!Set.COUNT.equals(Set.valueOf(setType.toUpperCase())) && !Set.TIMER.equals(Set.valueOf(setType.toUpperCase()))) {
            throw new ExerciseException(ErrorCode.INVALID_SET_TYPE);
        }
    }
}
