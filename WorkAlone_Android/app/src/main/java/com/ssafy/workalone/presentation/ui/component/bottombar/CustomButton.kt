package com.ssafy.workalone.presentation.ui.component.bottombar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50

@Composable
fun CustomButton(
    text: String,
    contentColor: Color = WalkOneGray50,
    backgroundColor: Color = WalkOneBlue500,
    borderColor: Color = WalkOneBlue500,
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
            .height(50.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = contentColor
        )
    }
}

@Preview
@Composable
fun previewCustomButton(){
    CustomButton("확인", onClick = {})
}