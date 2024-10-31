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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.ssafy.workalone.R
import com.ssafy.workalone.data.local.ExerciseRecordDatabase
import com.ssafy.workalone.presentation.ui.component.CloseButton
import com.ssafy.workalone.presentation.ui.component.CustomButton
import com.ssafy.workalone.presentation.ui.component.ExerciseRecord
import com.ssafy.workalone.presentation.ui.component.ExerciseRecordDetail
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray100
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray300
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.viewmodels.ExerciseRecordViewModel
import com.ssafy.workalone.presentation.viewmodels.ExerciseViewModel

data class IntegratedExerciseRecordData(
    val title: String,
    val exerciseCount: String,
    val exerciseDuration: Int,
    val calorie: Int
)

@Composable
fun IntegratedCompleteView() {
    val integratedExerciseRecordDataList = listOf(
        IntegratedExerciseRecordData("스쿼트","3세트 X 15회", 1801, 300),
        IntegratedExerciseRecordData("푸쉬업","4세트 X 12회", 1901, 250),
        IntegratedExerciseRecordData("플랭크","5세트 X 10회", 2000, 200)
    )

    val totalTime: Int = 3000
    val totalCalorie: Int = 300

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
                CloseButton(onClick = { /* X 버튼 클릭 이벤트 */ })
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
                .background(color = WalkOneGray50)
                .padding(25.dp),
//            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "운동 기록",
                fontWeight = FontWeight.Black,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
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
                        "${totalTime/60}:${String.format("%02d", totalTime % 60)}"
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
                        "${totalCalorie}Kcal"
                    )
                }
            }
        }

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
                    text = "기록 상세",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )

//                Spacer(modifier = Modifier.height(10.dp))

                integratedExerciseRecordDataList.forEach{ record ->

                    Column {
                        ExerciseRecordDetail(
                            record.title,
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

            //확인 버튼
            CustomButton(
                text = "확인",
                onClick = { /* 버튼 클릭 이벤트 */}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIntegratedCompleteView() {
//    IntegratedCompleteView()
}