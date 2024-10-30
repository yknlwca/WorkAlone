package com.ssafy.workalone.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.component.AppBarView
import com.ssafy.workalone.presentation.ui.component.ExerciseItem
import com.ssafy.workalone.presentation.viewmodels.ExerciseViewModel

@Composable
fun ExerciseListView(navController: NavController, viewModel: ExerciseViewModel) {
    val exerciseList = viewModel.getAllExercises.collectAsState(initial = listOf())

    Scaffold(
        topBar = {
            AppBarView(title = "운동 선택", navController = navController)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(exerciseList.value) { exercise ->
                ExerciseItem(exercise = exercise) {
                    navController.navigate(Screen.ExerciseDetail.createRoute(exercise.id))
                }
            }
        }
    }
}
