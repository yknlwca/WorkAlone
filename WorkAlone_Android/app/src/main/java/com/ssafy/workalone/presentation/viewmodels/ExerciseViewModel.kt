package com.ssafy.workalone.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.workalone.data.model.Exercise
import com.ssafy.workalone.data.repository.ExerciseRepository
import com.ssafy.workalone.presentation.navigation.Graph
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ExerciseViewModel(private val exerciseRepository: ExerciseRepository = Graph.exerciseRepository) :
    ViewModel() {

    lateinit var getAllExercises: Flow<List<Exercise>>

    init {
        viewModelScope.launch {
            getAllExercises = exerciseRepository.getExercises()
        }
    }


    fun getExerciseById(id: Long): Flow<Exercise> {
        return exerciseRepository.getExerciseById(id)
    }

}

