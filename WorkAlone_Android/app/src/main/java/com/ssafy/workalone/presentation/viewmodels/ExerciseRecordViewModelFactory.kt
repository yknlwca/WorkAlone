//package com.ssafy.workalone.presentation.viewmodels
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.ssafy.workalone.data.repository.ExerciseRecordRepository
//
//class ExerciseRecordViewModelFactory(private val exerciseRecordRepository: ExerciseRecordRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ExerciseRecordViewModel::class.java)) {
//            return ExerciseRecordViewModel(exerciseRecordRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}