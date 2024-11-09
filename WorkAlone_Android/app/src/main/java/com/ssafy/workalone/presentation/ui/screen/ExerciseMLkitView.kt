package com.ssafy.workalone.presentation.ui.screen

import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.MainActivity
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.component.CustomDialog
import com.ssafy.workalone.presentation.ui.component.ExerciseTimer
import com.ssafy.workalone.presentation.ui.component.RepCounter
import com.ssafy.workalone.presentation.ui.component.RestTime
import com.ssafy.workalone.presentation.ui.component.StopwatchScreen
import com.ssafy.workalone.presentation.viewmodels.ExerciseMLKitViewModel

@Composable
fun ExerciseMLkitView(
    exerciseType: String?,
    viewModel: ExerciseMLKitViewModel,
) {
    val context = LocalContext.current
    fun navigateToHome(){
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("startDestination", Screen.Home.route)
        context.startActivity(intent)
    }
    Scaffold(
        topBar = { StopwatchScreen(true, viewModel) },
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
        content = {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                val context = LocalContext.current
                val density = LocalDensity.current
                // 쉬는 시간이 아닐 때 보여줄 Column 구성 요소들
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .background(Color.Transparent),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    if (exerciseType != null) {
                        Text(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            text = exerciseType,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        if (exerciseType != "플랭크")
                            RepCounter(viewModel)
                        else
                            ExerciseTimer(viewModel)
                    }
                }

                // 쉬는 시간일 때 RestTime이 최상단에 위치하도록 설정
                if (viewModel.isResting.value == true) {
                    RestTime(viewModel)
                }
                if(viewModel.isExit.value){
                    CustomDialog({ viewModel.cancelExit() },{ navigateToHome() },"운동을 완료해야 기록이 저장됩니다.\n정말로 종료하시겠어요?")
                }
                if(!viewModel.isExercising.value && !viewModel.isExit.value){

                    val yOffsetInPx = with(density) { 240.dp.toPx().toInt() }
                    val toast = Toast.makeText(context,"운동을 다시 진행하고 싶을 때 \"시작\"이라고 말해보세요!", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.BOTTOM,0,yOffsetInPx)
                    toast.show()

                }
                BackHandler {
                    viewModel.clickExit()
                }

            }
        }
    )
}