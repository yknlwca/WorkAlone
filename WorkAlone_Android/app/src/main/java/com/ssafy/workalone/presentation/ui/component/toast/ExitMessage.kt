package com.ssafy.workalone.presentation.ui.component.toast

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.R
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray800

@Composable
fun ExitMessage(){

        Row(
            Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
                .background(WalkOneGray800)
                .padding(vertical = 8.dp, horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.notice),
                contentDescription = "Exit message",
                modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "운동을 다시 진행하고 싶을 때 \"시작\"이라고 말해보세요!",
                color = Color.White,
                fontSize = 14.sp)
        }
}

@Preview
@Composable
fun previewExitMessage(){
    ExitMessage()
}

