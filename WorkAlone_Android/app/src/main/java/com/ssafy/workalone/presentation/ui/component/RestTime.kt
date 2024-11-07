package com.ssafy.workalone.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray700
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray900
import com.ssafy.workalone.presentation.viewmodels.ExerciseMLKitViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RestTime(
    viewModel: ExerciseMLKitViewModel
) {
    LaunchedEffect(viewModel.stage.value) {
        viewModel.stageDescribe(viewModel.stage.value)
    }
    var isTenSecondsLeft by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while(viewModel.restTime.value>0) {
            delay(1000L)
            viewModel.countDownRestTime()
            if(viewModel.restTime.value <= 10 && viewModel.stage.value != "ready") {
                viewModel.changeStage()
                isTenSecondsLeft = true
            }

        }
    }

        if(!viewModel.isExercising.value!!){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WalkOneGray900.copy(alpha = 0.9f))
                    .padding(20.dp)
            ) {
                if(viewModel.stage.value == "rest")
                    ConfettiAnimation(8000L)

                Text(
                    text =
                    if (isTenSecondsLeft) {
                        viewModel.preSetText.value
                    } else {
                        viewModel.restText.value
                    }
                    ,
                    color = WalkOneGray50,
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.TopStart).padding(top = 50.dp)
                )

                Text(
                    text = "${viewModel.restTime.value}",
                    color = WalkOneGray50,
                    style = TextStyle(
                        fontSize = 120.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }


}

//@Preview
//@Composable
//fun previewRestTimeModal() {
//    RestTime(true, 60)
//}