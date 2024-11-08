package com.ssafy.workalone.presentation.viewmodels.Exercise

import androidx.lifecycle.ViewModel
import com.ssafy.workalone.data.model.exercise.Challenge
import com.ssafy.workalone.data.model.exercise.Exercise
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

class ExerciseViewModel(private val exerciseRepository: ExerciseRepository = ExerciseRepository()) :
    ViewModel() {
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage

    val getAllChallenge: Flow<List<Challenge>> = flow {
        emitAll(exerciseRepository.getChallenges())
    }.flowOn(Dispatchers.IO).catch { e -> handleException(e, _errorMessage) }

    fun getExerciseById(exerciseId: Long): Flow<List<Exercise>> = flow {
        emitAll(exerciseRepository.getExerciseByIdAndType(exerciseId))
    }.flowOn(Dispatchers.IO).catch { e ->
        handleException(e, _errorMessage)
    }

    // 에러 메시지 초기화 함수
    fun clearErrorMessage() {
        _errorMessage.value = null
    }


    private val _isFullScreen = MutableStateFlow(false)
    val isFullScreen:StateFlow<Boolean> get() = _isFullScreen

    private val _playBackPosition = MutableStateFlow<Long?>(null)
    val playBackPosition:StateFlow<Long?> get() = _playBackPosition

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