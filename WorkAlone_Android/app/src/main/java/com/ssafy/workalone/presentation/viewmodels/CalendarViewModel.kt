package com.ssafy.workalone.presentation.viewmodels

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.workalone.data.repository.ExerciseSummaryRepository
import com.ssafy.workalone.global.exception.CustomException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.LocalDate

@SuppressLint("NewAPI")
class CalendarViewModel(
    private val exerciseSummaryRepository: ExerciseSummaryRepository = ExerciseSummaryRepository()
) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _selectedDate: MutableState<LocalDate?> = mutableStateOf(LocalDate.now())
    private val _markedDates = MutableStateFlow<List<String>>(emptyList())

    val selectedDate: MutableState<LocalDate?> get() = _selectedDate
    val markedDates: StateFlow<List<String>> = _markedDates.asStateFlow()

    fun changeSelectedDate(date: LocalDate) {
        if (_selectedDate.value == date) {
            return
        } else {
            _selectedDate.value = date
        }
    }

    private fun chageMarkedDates(dataList: List<String>){
        _markedDates.value = dataList
    }

    fun loadMarkedDates(){
        viewModelScope.launch {
            exerciseSummaryRepository.getCompletedExerciseDate()
                .catch { e -> handleException(e) }
                .collect{ dataList ->
                    chageMarkedDates(dataList)
                }
        }
    }

    private fun handleException(exception: Throwable) {
        when (exception) {
            is CustomException.NetworkException -> {
                // 네트워크 처리 문제
                Log.d("Network ViewModel", "Network error : ${exception.message}")
            }

            is CustomException.ServerException -> {
                // 서버 에러 처리
                Log.d("Server ViewModel", "Server error : ${exception.message}")
            }

            is CustomException.ClientException -> {
                // 안드로이드 에러 처리
                Log.d("Client ViewModel", "Client error : ${exception.message}")
            }

            is CustomException.UnknownException -> {
                // 알 수 없는 에러 찾아 보기
                Log.d("Unknown ViewModel", "Unknown error : ${exception.message}")
            }
        }
    }
}