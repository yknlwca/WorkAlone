package com.ssafy.workalone.presentation.ui.screen

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.component.AppBarView
import com.ssafy.workalone.presentation.ui.component.ExerciseItem
import com.ssafy.workalone.presentation.viewmodels.ExerciseViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseListView(navController: NavController, viewModel: ExerciseViewModel = ExerciseViewModel()) {
    val challengeList = viewModel.getAllChallenge.collectAsState(initial = listOf())
    val errorMessage by viewModel.errorMessage.collectAsState()

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
                        navController.navigate(
                            Screen.ExerciseDetail.createRoute(
                                challenge.groupId
                            )
                        )
                    }
                }
            }
        }
    }
}
