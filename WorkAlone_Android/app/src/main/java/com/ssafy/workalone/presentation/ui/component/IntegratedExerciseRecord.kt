package com.ssafy.workalone.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.workalone.R
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray100
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray300


// 운동 완료 화면 운동 기록 컴포넌트(통합형)
@Composable
fun IntegratedExerciseRecord(
    exerciseDuration: Int,
    calorie: Int
){
    val hours: Int = exerciseDuration/3600
    val minutes: Int = (exerciseDuration%3600)/60
    val seconds: Int = exerciseDuration%60

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = WalkOneGray100),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp),
        ) {
            //Box1
            ExerciseRecord(
                painterResource(R.drawable.time),
                "총 운동 시간",
                if (hours > 0){
                    "${String.format("%02d", hours)}:${String.format("%02d", minutes)}:${String.format("%02d", seconds)}"
                } else {
                    "${String.format("%02d", minutes)}:${String.format("%02d", seconds)}"
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Divider(
                color = WalkOneGray300,
                thickness = 1.dp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            ExerciseRecord(
                painterResource(R.drawable.fire),
                "총 소모 칼로리",
                "${calorie}Kcal"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCompleteView() {
    IntegratedExerciseRecord(30001, 300)
}