package com.ssafy.workalone

import android.widget.Space
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.components.CloseButton
import com.ssafy.workalone.components.CustomButton
import com.ssafy.workalone.ui.theme.WalkOneBlue500
import com.ssafy.workalone.ui.theme.WalkOneGray500
import com.ssafy.workalone.ui.theme.WalkOneGray100
import com.ssafy.workalone.ui.theme.WalkOneGray300
import com.ssafy.workalone.ui.theme.WalkOneGray50

@Composable
fun CompleteView(
    workoutCount: String = "3세트 X 15회",
    minutes: String = "10",
    seconds: String = "00",
    totalCaloriesBurned: Int = 100
){
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
                .padding(30.dp),

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
                .fillMaxHeight()
                .background(color = WalkOneGray50)
                .padding(30.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
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
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                Arrangement.SpaceBetween
                            ) {
                                Row {
                                    Image(
                                        painter = painterResource(R.drawable.muscle),
                                        contentDescription = "Muscle Icon",
                                        modifier = Modifier
                                            .size(24.dp)
                                            .graphicsLayer(scaleX = -1f),
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = "운동횟수",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }

                                Box {
                                    Text(
                                        text = "${workoutCount}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Divider(
                            color = WalkOneGray300,
                            thickness = 1.dp,
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        //Box2
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                Arrangement.SpaceBetween
                            ) {
                                Row {
                                    Image(
                                        painter = painterResource(R.drawable.time),
                                        contentDescription = "Time Icon",
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = "총 운동 시간",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }

                                Box {
                                    Text(
                                        text = "${minutes}:${seconds}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Divider(
                            color = WalkOneGray300,
                            thickness = 1.dp,
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        //Box3
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                Arrangement.SpaceBetween
                            ) {
                                Row {
                                    Image(
                                        painter = painterResource(R.drawable.fire),
                                        contentDescription = "Calorie Icon",
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = "총 소모 칼로리",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }

                                Box {
                                    Text(
                                        text = "${totalCaloriesBurned}kcal",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            //확인 버튼
            CustomButton(
                text = "확인",
                onClick = { /* 버튼 클릭 이벤트 */})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCompleteView() {
    CompleteView()
}