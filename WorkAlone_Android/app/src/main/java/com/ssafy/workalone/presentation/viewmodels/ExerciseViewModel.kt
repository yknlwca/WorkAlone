package com.ssafy.workalone.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ssafy.workalone.data.model.Exercise
import com.ssafy.workalone.data.repository.ExerciseRepository
import com.ssafy.workalone.global.exception.CustomException
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
    // 내부에서만 변경 가능한 MutableLiveData
    private val _errorMessage = MutableStateFlow<String?>(null)
    // 외부에서 읽기 전용 LiveData
    val errorMessage: StateFlow<String?>
        get() = _errorMessage

    val getAllExercises: Flow<List<Exercise>> = flow {
        emitAll(exerciseRepository.getExercises())
    }.flowOn(Dispatchers.IO).catch { e -> handleException(e) }

    fun getExerciseById(exerciseId: Long, exerciseType: String): Flow<List<Exercise>> = flow {
        emitAll(exerciseRepository.getExerciseByIdAndType(exerciseId, exerciseType))
    }.flowOn(Dispatchers.IO).catch { e ->
        handleException(e)
    }


    private fun handleException(exception: Throwable) {
        when (exception) {
            is CustomException.NetworkException -> {
                // 네트워크 처리 문제
                Log.d("Network ViewModel", "Network error : ${exception.message}")
                _errorMessage.value = exception.message
            }

            is CustomException.ServerException -> {
                // 서버 에러 처리
                Log.d("Server ViewModel", "Server error : ${exception.message}")
                _errorMessage.value = exception.message
            }

            is CustomException.ClientException -> {
                // 안드로이드 에러 처리
                Log.d("Client ViewModel", "Client error : ${exception.message}")
                _errorMessage.value = exception.message
            }

            is CustomException.UnknownException -> {
                // 알 수 없는 에러 찾아 보기
                Log.d("Unknown ViewModel", "Unknown error : ${exception.message}")
                _errorMessage.value = exception.message
            }
        }
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