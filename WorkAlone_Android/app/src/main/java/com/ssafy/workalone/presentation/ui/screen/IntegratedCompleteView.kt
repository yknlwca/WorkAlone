package com.ssafy.workalone.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
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
import com.ssafy.workalone.presentation.ui.component.ConfettiAnimation
import com.ssafy.workalone.presentation.ui.component.CustomButton
import com.ssafy.workalone.presentation.ui.component.ExerciseRecordDetail
import com.ssafy.workalone.presentation.ui.component.IntegratedExerciseRecord
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray300
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import kotlin.math.roundToInt

data class IntegratedExerciseRecordData(
    val title: String,
    val setCount: Int,
    val exerciseCount: Int,
    val exerciseDuration: Int,
    val calorie: Int
)

// 운동 완료 화면(통합형)
@Composable
fun IntegratedCompleteView(navController: NavController) {
    val integratedExerciseRecordDataList: List<IntegratedExerciseRecordData> = listOf(
        IntegratedExerciseRecordData("스쿼트",3, 15,1000, 200),
        IntegratedExerciseRecordData("푸쉬업",3, 0, 100,300),
        IntegratedExerciseRecordData("플랭크",3, 15,2000, 400),
        IntegratedExerciseRecordData("플랭크",3, 15,4000, 500),
        IntegratedExerciseRecordData("플랭크",3, 0, 22200,250),
        IntegratedExerciseRecordData("플랭크",3, 0, 300,600),
        IntegratedExerciseRecordData("플랭크",3, 15,10, 700),
    )

    val totalTime: Int = 3000
    val totalCalorie: Int = 300
    val exerciseType: String = "윗몸일으키기"
    val weight = 60

    //전체 화면
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WalkOneGray300)
    ) {
        Column(
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
                    horizontalArrangement = Arrangement.End
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

            //운동 기록
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = WalkOneGray50)
                    .padding(25.dp),
            ) {
                Text(
                    text = "운동 기록",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                // 운동 기록 표
                IntegratedExerciseRecord(
                    totalTime,
                    totalCalorie
                )
            }

            //기록 상세
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = WalkOneGray50)
                    .padding(25.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                //기록 상세 문구 및 표
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 20.dp),
                ) {
                    Column {
                        Text(
                            text = "기록 상세",
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                        )
                        LazyColumn {
                            // 기록 상세 표
                            items(integratedExerciseRecordDataList) { record ->
//                                val calorie: Int =
//                                    if (exerciseType == "스쿼트") {
//                                        (6.0 * weight * (record.totalExerciseDuration / 3600.0)).roundToInt()
//                                    } else if (exerciseType == "푸쉬업") {
//                                        (4.0 * weight * (record.totalExerciseDuration / 3600.0)).roundToInt()
//                                    } else if (exerciseType == "윗몸일으키기") {
//                                        (4.0 * weight * (record.totalExerciseDuration / 3600.0)).roundToInt()
//                                    } else if (exerciseType == "플랭크") {
//                                        (3.0 * weight * (record.totalExerciseDuration / 3600.0)).roundToInt()
//                                    } else {
//                                        0
//                                    }
                                Column {
                                    ExerciseRecordDetail(
                                        record.title,
                                        record.setCount,
                                        record.exerciseCount,
                                        record.exerciseDuration,
                                        record.calorie
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Divider(
                                        color = WalkOneGray300,
                                        thickness = 1.dp,
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))
                                }

                            }
                        }
                    }
                }
                //확인 버튼
                CustomButton(
                    text = "확인",
                    onClick = { /* 버튼 클릭 이벤트 */ }
                )
            }
        }
        ConfettiAnimation()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIntegratedCompleteView() {
    val fake = rememberNavController()
    IntegratedCompleteView(fake)
}