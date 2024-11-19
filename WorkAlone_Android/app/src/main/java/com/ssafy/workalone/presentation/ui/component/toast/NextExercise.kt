package com.ssafy.workalone.presentation.ui.component.toast

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.R
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray300
import com.ssafy.workalone.presentation.viewmodels.ExerciseMLKitViewModel

@Composable
fun NextExercise(viewModel: ExerciseMLKitViewModel){
    Row(
        Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(WalkOneBlue500)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        var imageResId = R.drawable.w_logo
        if (viewModel.nowExercise.value.title == "스쿼트") {
            imageResId = R.drawable.squat
        } else if (viewModel.nowExercise.value.title=="푸쉬업") {
            imageResId = R.drawable.push_up
        } else if (viewModel.nowExercise.value.title == "윗몸 일으키기") {
            imageResId = R.drawable.sit_up
        } else if (viewModel.nowExercise.value.title == "플랭크") {
            imageResId = R.drawable.plank
        }
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Exercise Image",
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(5.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(text = "다음 운동",
                color = WalkOneGray300,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = viewModel.nowExercise.value.title,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}
