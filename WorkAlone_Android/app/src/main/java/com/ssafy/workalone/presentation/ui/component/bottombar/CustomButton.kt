package com.ssafy.workalone.presentation.ui.component.bottombar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50

@Composable
fun CustomButton(
    text: String,
    contentColor: Color = WalkOneGray50,
    backgroundColor: Color = WalkOneBlue500,
    borderColor: Color = WalkOneBlue500,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = enabled
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = contentColor
        )
    }
}

@Composable
fun NavigationButtons(
    navController: NavController,
    id: Long,
    seq: Int,
    isLast: Boolean,
    isFirst: Boolean,
    onStartExercise: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        // "이전" 버튼은 첫 번째 운동이 아닐 때만 표시
        if (!isFirst) {
            Box(modifier = Modifier.weight(1f)) {
                NavigationButton(
                    text = "이전",
                    backgroundColor = WalkOneGray50,
                    contentColor = WalkOneBlue500,
                    borderColor = WalkOneBlue500,
                    onClick = {
                        val prevSeq = seq - 1
                        navController.navigate(Screen.ExerciseDetail.createRoute(id, prevSeq))
                    }
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
        }

        // 마지막 운동일 때는 "시작하기" 버튼, 아니면 "다음 운동" 버튼
        if (isLast) {
            // 마지막 운동일 때는 "이전"과 "시작하기" 버튼이 모두 있어야 한다.
            Box(modifier = Modifier.weight(1f)) {
                CustomButton(
                    text = "시작하기",
                    onClick = onStartExercise
                )
            }
        } else {
            // 마지막 운동이 아니면 "다음 운동" 버튼
            Box(modifier = Modifier.weight(1f)) {
                CustomButton(
                    text = "다음",
                    onClick = {
                        val nextSeq = seq + 1
                        navController.navigate(Screen.ExerciseDetail.createRoute(id, nextSeq))
                    }
                )
            }
        }
    }
}


@Composable
fun NavigationButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    contentColor: Color,
    borderColor: Color
) {
    CustomButton(
        text = text,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        borderColor = borderColor,
        onClick = onClick
    )
}


@Preview
@Composable
fun previewCustomButton(){
    CustomButton("확인", onClick = {})
}