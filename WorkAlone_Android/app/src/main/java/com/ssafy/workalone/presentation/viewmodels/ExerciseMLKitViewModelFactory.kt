package com.ssafy.workalone.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ExerciseMLKitViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseMLKitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExerciseMLKitViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

