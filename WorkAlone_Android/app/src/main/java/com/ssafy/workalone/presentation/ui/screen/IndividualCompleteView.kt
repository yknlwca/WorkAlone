package com.ssafy.workalone.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.workalone.R
import com.ssafy.workalone.presentation.ui.component.CloseButton
import com.ssafy.workalone.presentation.ui.component.CustomButton
import com.ssafy.workalone.presentation.ui.component.IndividualExerciseRecord
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray300
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import kotlin.math.roundToInt

//운동 완료 화면(개별형)
@Composable
fun IndividualCompleteView(navController: NavController) {

    val exerciseCount: String = "3세트 X 15회"
    val exerciseDuration: Int = 1808
    val exerciseType: String = "푸쉬업"
    val weight = 60
    val calorie: Int =
        if (exerciseType == "스쿼트") {
            (6.0 * weight * (exerciseDuration/3600.0)).roundToInt()
        } else if (exerciseType == "푸쉬업") {
            (4.0 * weight * (exerciseDuration/3600.0)).roundToInt()
        } else if (exerciseType == "윗몸일으키기") {
            (4.0 * weight * (exerciseDuration/3600.0)).roundToInt()
        } else if (exerciseType == "플랭크") {
            (3.0 * weight * (exerciseDuration/3600.0)).roundToInt()
        } else {
            0
        }

    //전체 화면
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WalkOneGray300),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        // 운동 인증 완료 문구 & 이미지
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = WalkOneGray50)
                .padding(25.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Row의 끝에 배치
            ) {
                CloseButton(navController)
            }

            Text(
                text = "운동 인증 완료!",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.work),
                contentDescription = "Workout Complete Icon",
                modifier = Modifier.size(120.dp)
            )
        }

        //운동기록
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = WalkOneGray50)
                .padding(25.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "운동 기록",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                //운동 기록 표
                IndividualExerciseRecord(
                    exerciseCount,
                    exerciseDuration,
                    calorie
                )
            }

            //확인 버튼
            CustomButton(
                text = "확인",
                onClick = { /* 버튼 클릭 이벤트 */ })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCompleteView() {
    val fakeNavController = rememberNavController()
    IndividualCompleteView(navController = fakeNavController)
}
