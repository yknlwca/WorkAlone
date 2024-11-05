package com.ssafy.workalone.presentation.ui.component

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray500

@Composable
fun RepCounter(){

    var isPaused: Boolean = false
    val totalReps: Int = 15
    var currentReps by remember { mutableStateOf(0) }
    val totalSets: Int = 3
    var currentSet by remember { mutableStateOf(1) }

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
                text = "${currentReps}",
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alignByBaseline()
            )
            Text(
                text = "/",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alignByBaseline()
            )
            Text(
                text = "${totalReps}회",
                fontSize = 28.sp,
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
                text = "${currentSet}세트",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = WalkOneBlue500,
                modifier = Modifier
                    .alignByBaseline()
            )
            Text(
                text = "|",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = WalkOneGray500,
                modifier = Modifier
                    .alignByBaseline()
            )
            Text(
                text = "${totalSets}세트",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = WalkOneGray500,
                modifier = Modifier
                    .alignByBaseline()
            )
        }

        if(!isPaused){
            Row(
                modifier = Modifier
                    .fillMaxWidth()

            ){
                CustomButton(
                    "일시정지",
                    WalkOneBlue500,
                    WalkOneGray50,
                    WalkOneBlue500,
                    onClick = {}
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
                        onClick = {}
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
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewRepCounter(){
    RepCounter()
}