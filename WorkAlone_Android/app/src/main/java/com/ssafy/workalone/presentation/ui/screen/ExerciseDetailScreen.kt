package com.ssafy.workalone.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.workalone.data.model.Exercise
import com.ssafy.workalone.presentation.ui.component.AppBarView
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme
import com.ssafy.workalone.presentation.viewmodels.ExerciseViewModel


@Composable
fun ExerciseDetailScreen(
    navController: NavController,
    viewModel: ExerciseViewModel,
    id: Long
) {
    val scaffoldState = rememberScaffoldState()
    val exercise = viewModel.getExerciseById(id)
        .collectAsState(initial = Exercise(0L, "", "", "", "", "", "", 0))
    Scaffold(
        topBar = {
            AppBarView(
                title = exercise.value.title,
                onBackNavClicked = { navController.navigateUp() }
            )
        },
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = exercise.value.title, style = WorkAloneTheme.typography.Heading03)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = exercise.value.subTitle, style = WorkAloneTheme.typography.Heading04)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = exercise.value.content)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = exercise.value.basicPose, style = WorkAloneTheme.typography.Body02)
            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = exercise.value.movement, style = WorkAloneTheme.typography.Body02)
            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = exercise.value.breath, style = WorkAloneTheme.typography.Body02)
        }
    }
}
