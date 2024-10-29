package com.ssafy.workalone.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.workalone.R
import com.ssafy.workalone.data.model.Exercise
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.component.AppBarView
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray700
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme
import com.ssafy.workalone.presentation.viewmodels.ExerciseViewModel

@Composable
fun ExerciseListScreen(navController: NavController, viewModel: ExerciseViewModel) {
    val context = LocalContext.current
    val exerciseList = viewModel.getAllExercises.collectAsState(initial = listOf())

    Scaffold(
        topBar = {
            AppBarView(title = "운동 선택")
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

@Composable
fun ExerciseItem(exercise: Exercise, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onClick() },
        elevation = 8.dp,
        backgroundColor = WalkOneGray50,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(WalkOneGray50)
            ) {
                val imageResId = exercise.picture ?: R.drawable.time
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Exercise Image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = exercise.title,
                    style = WorkAloneTheme.typography.Heading02,
                    color = WalkOneBlue500,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = exercise.subTitle,
                    style = WorkAloneTheme.typography.Body01,
                    color = WalkOneGray700,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
