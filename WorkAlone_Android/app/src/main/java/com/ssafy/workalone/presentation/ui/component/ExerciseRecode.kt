package com.ssafy.workalone.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue400


@Composable
fun ExerciseRecode(
    title: String,
    exerciseCount: String,
    exerciseDuration: Int,
    calorie: Int
){
    val minutes: Int = exerciseDuration/60
    val seconds: Int = exerciseDuration%60

    Column(
        modifier = Modifier
            .background(WalkOneGray50)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = WalkOneBlue400,
                fontWeight = FontWeight.Black,
                fontSize = 16.sp
            )

            Text(
                text = exerciseCount,
                fontWeight = FontWeight.Black,
                fontSize = 16.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "운동 시간",
                fontSize = 14.sp,
            )

            Text(
                text = "${minutes}:${String.format("%02d", seconds)}",
                fontSize = 14.sp,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "소모 칼로리",
                fontSize = 14.sp,
            )

            Text(
                text = "${calorie}Kcal",
                fontSize = 14.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseRecodePreview() {
    ExerciseRecode("스쿼트","3세트 X 15회", 1800, 300)
}