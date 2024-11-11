package com.ssafy.workalone.presentation.viewmodels.exercise

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.workalone.data.local.ExerciseInfoPreferenceManager
import com.ssafy.workalone.data.model.exercise.Challenge
import com.ssafy.workalone.data.model.exercise.Exercise
import com.ssafy.workalone.data.model.exercise.ExerciseData
import com.ssafy.workalone.data.repository.ExerciseRepository
import com.ssafy.workalone.global.exception.handleException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val exerciseRepository: ExerciseRepository = ExerciseRepository(),
    context: Context
) : ViewModel() {
    private val exerciseInfoPreferenceManager = ExerciseInfoPreferenceManager(context)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage


    val getAllChallenge: Flow<List<Challenge>> = flow {
        emitAll(exerciseRepository.getChallenges())
    }.flowOn(Dispatchers.IO).catch { e -> handleException(e, _errorMessage) }

    fun getExerciseById(exerciseId: Long): Flow<List<Exercise>> = flow {
        emitAll(exerciseRepository.getExerciseById(exerciseId))
    }.flowOn(Dispatchers.IO).catch { e ->
        handleException(e, _errorMessage)
    }

    // 운동 리스트 SharedPreferences에 저장
    fun saveExercisesPreferences(exercises: List<Exercise>) {
        viewModelScope.launch {
            val exerciseDataList = exercises.map { exercise ->
                ExerciseData(
                    exerciseId = exercise.exerciseId,
                    seq = exercise.seq,
                    title = exercise.title,
                    restBtwSet = exercise.restBtwSet,
                    exerciseSet = exercise.exerciseSet,
                    exerciseRepeat = exercise.exerciseRepeat,
                    type = exercise.setType,
                )
            }
            exerciseInfoPreferenceManager.setExerciseList(exerciseDataList)
        }
    }

    // 에러 메시지 초기화 함수
    fun clearErrorMessage() {
        _errorMessage.value = null
    }


    private val _isFullScreen = MutableStateFlow(false)
    val isFullScreen: StateFlow<Boolean> get() = _isFullScreen

    private val _playBackPosition = MutableStateFlow<Long?>(null)
    val playBackPosition: StateFlow<Long?> get() = _playBackPosition

    // 전체 화면 토글 함수
    fun toggleFullScreen(position: Long?) {
        _playBackPosition.value = position
        _isFullScreen.value = !_isFullScreen.value
    }

    // 플레이백 위치 업데이트 함수
    fun updatePlayBackPosition(position: Long) {
        _playBackPosition.value = position
    }
}