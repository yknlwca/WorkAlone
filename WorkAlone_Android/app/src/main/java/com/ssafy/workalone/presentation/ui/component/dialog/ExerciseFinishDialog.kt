package com.ssafy.workalone.presentation.ui.component.dialog

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.presentation.ui.component.bottombar.CustomButton

@Composable
fun ExerciseFinishDialog(confirmRequest: () -> Unit){
    AlertDialog(
        modifier = Modifier.border(
            width = 2.dp,
            color = Color.Black,
            RoundedCornerShape(25.dp)
        ).fillMaxWidth(),
        title = { Text(text = "축하합니다!") },
        text = { Text(text = "챌린지를 완료하셨습니다.\n인증 완료 화면으로 이동합니다.", fontSize = 16.sp) },
        onDismissRequest = {},
        confirmButton = {
            Box(
                modifier = Modifier.width(80.dp)
            ) {
                CustomButton("확인", onClick = {confirmRequest()})
            }
        },


        )
}

@Preview
@Composable
fun previewDia(){
    ExerciseFinishDialog { {} }
}