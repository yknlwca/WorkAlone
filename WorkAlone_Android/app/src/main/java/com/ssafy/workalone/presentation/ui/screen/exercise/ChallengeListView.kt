package com.ssafy.workalone.presentation.ui.screen.exercise

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.workalone.data.local.ExerciseInfoPreferenceManager
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.component.exercise.ExerciseItem
import com.ssafy.workalone.presentation.ui.component.topbar.AppBarView
import com.ssafy.workalone.presentation.viewmodels.exercise.ExerciseViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChallengeListView(navController: NavController, viewModel: ExerciseViewModel = ExerciseViewModel(context = LocalContext.current)) {
    val challengeList = viewModel.getAllChallenge.collectAsState(initial = listOf())
    val errorMessage by viewModel.errorMessage.collectAsState()
    val preferenceManager = ExerciseInfoPreferenceManager(LocalContext.current)

    Scaffold(
        topBar = {
            AppBarView(title = "운동 선택", navController = navController)
        }
    ) {
        errorMessage?.let { message ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = message, color = Color.Red, style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.clearErrorMessage() }) {
                    Text(text = "다시 시도")
                }
            }
        } ?: run {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(challengeList.value) { challenge ->
                    ExerciseItem(challenge = challenge) {

                            challenge.restBtwExercise?.let { it1 ->
                                preferenceManager.setRestBtwExercise(
                                    it1
                                )
                            }
                        navController.navigate(
                            Screen.ExerciseDetail.createRoute(
                                challenge.groupId, 1
                            )
                        )
                    }
                }
            }
        }
    }
}
