//package com.ssafy.workalone.presentation.viewmodels
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.ssafy.workalone.data.model.ExerciseRecord
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class ExerciseRecordViewModel(private val exerciseRecordRepository: ExerciseRecordRepository): ViewModel() {
//    private val _exerciseRecords = MutableStateFlow<List<ExerciseRecord>>(emptyList())
//    val exerciseRecords: StateFlow<List<ExerciseRecord>> = _exerciseRecords
//
//    fun addExerciseRecords(exerciseRecords: List<ExerciseRecord>){
//        viewModelScope.launch {
//            exerciseRecordRepository.insertExerciseRecords(exerciseRecords)
//        }
//    }
//
//    fun fetchExerciseRecords() {
//        viewModelScope.launch {
//            exerciseRecordRepository.getAllExerciseRecords().collect{ records ->
//                _exerciseRecords.value = records
//            }
//        }
//    }
//}