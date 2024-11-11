package com.ssafy.workalone.presentation.ui.component.bottombar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.viewmodels.ExerciseMLKitViewModel
import kotlinx.coroutines.delay

// 운동 타이머 컴포넌트
@Composable
fun ExerciseTimer(viewModel: ExerciseMLKitViewModel){

    var isExercise = viewModel.isExercising.value
    var goalTime by viewModel.totalRep
    val totalSets by viewModel.totalSet
    var currentSet by viewModel.nowSet

    val configuration = LocalConfiguration.current

    LaunchedEffect(Unit) {
        while (goalTime > 0) {
            if (isExercise) {
                // 1초 대기
                delay(1000L)
                // 일시정지가 아닐 때만 감소
                if (isExercise == true) {
                    viewModel.decreaseTime()
                }
            } else {
                // 일시정지 상태에서 짧은 대기
                delay(100L)
            }
        }
    }

    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            modifier = Modifier
                .background(
                    color = WalkOneGray50,
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    ))
                .fillMaxWidth()
                .padding(25.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${String.format("%02d", goalTime/60)} : ${String.format("%02d", goalTime%60)}",
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .alignByBaseline()
                )
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${currentSet}세트 | ${totalSets}세트",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = WalkOneBlue500,
                    modifier = Modifier
                        .alignByBaseline()
                )
            }

            if(isExercise){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                ){
                    CustomButton(
                        "일시정지",
                        WalkOneBlue500,
                        WalkOneGray50,
                        WalkOneBlue500,
                        onClick = { viewModel.stopExercise()}
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ){
                        CustomButton(
                            "종료하기",
                            WalkOneBlue500,
                            WalkOneGray50,
                            WalkOneBlue500,
                            onClick = {viewModel.clickExit()}
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        CustomButton(
                            "계속하기",
                            WalkOneGray50,
                            WalkOneBlue500,
                            WalkOneBlue500,
                            onClick = { viewModel.startExercise() }
                        )
                    }
                }
            }
        }
    } else {
        Row(
            modifier = Modifier
                .background(
                    color = WalkOneGray50,
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    ))
                .fillMaxWidth()
                .padding(25.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${String.format("%02d", goalTime/60)} : ${String.format("%02d", goalTime%60)}",
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .alignByBaseline()
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${currentSet}세트 | ${totalSets}세트",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = WalkOneBlue500,
                    modifier = Modifier
                        .alignByBaseline()
                )
            }

            if(isExercise){
                Row(
                    modifier = Modifier
                        .weight(1f)
                ){
                    CustomButton(
                        "일시정지",
                        WalkOneBlue500,
                        WalkOneGray50,
                        WalkOneBlue500,
                        onClick = { viewModel.stopExercise() }
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .weight(1f)
                ){
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ){
                        CustomButton(
                            "종료하기",
                            WalkOneBlue500,
                            WalkOneGray50,
                            WalkOneBlue500,
                            onClick = {viewModel.clickExit()}
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        CustomButton(
                            "계속하기",
                            WalkOneGray50,
                            WalkOneBlue500,
                            WalkOneBlue500,
                            onClick = { viewModel.startExercise() }
                        )
                    }
                }
            }
        }
    }
}