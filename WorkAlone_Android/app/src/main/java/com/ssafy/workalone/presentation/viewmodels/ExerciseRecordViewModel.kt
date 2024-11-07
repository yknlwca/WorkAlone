package com.ssafy.workalone.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.ssafy.workalone.data.model.ExerciseRecord
import com.ssafy.workalone.data.repository.ExerciseRecordRepository
import com.ssafy.workalone.global.exception.handleException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExerciseRecordViewModel(private val exerciseRecordRepository: ExerciseRecordRepository) :
    ViewModel() {
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage

    val getExerciseRecords: Flow<List<ExerciseRecord>> = flow {
        emitAll(exerciseRecordRepository.getExersiceRecords())
    }.flowOn(Dispatchers.IO).catch { e -> handleException(e, _errorMessage) }
}