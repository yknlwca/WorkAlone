package com.ssafy.workalone.presentation.ui.component.dialog

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.presentation.ui.component.bottombar.CustomButton
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray900

@Composable
fun CustomDialog(
    onDismissRequest:()->Unit,
    onConfirmation:()->Unit,
    content:String,
){
    AlertDialog(
        modifier = Modifier.border(
            width = 2.dp,
            color = Color.Black,
            RoundedCornerShape(25.dp)
        ).fillMaxWidth(),
        text = { Text(text = content) },
        onDismissRequest = {onDismissRequest()},
        dismissButton = {
            Box(
                modifier = Modifier.width(80.dp)
            ) {
                TextButton(onClick = {onDismissRequest() },
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                    ,shape = RoundedCornerShape(5.dp)){
                    Text(
                        text = "취소",
                        fontSize = 16.sp,
                        color = WalkOneGray900
                    )
                }
            }
            },
        confirmButton = {
            Box(
                modifier = Modifier.width(80.dp)
            ) {
                CustomButton("종료", onClick = {onConfirmation()})
            }
             },


    )
}

@Preview
@Composable
fun previewDialog(){
    CustomDialog({},{},"운동을 완료해야 기록이 저장됩니다.\n 정말로 종료하시겠어요?")
}
