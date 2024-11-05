package com.ssafy.workalone.presentation.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray600
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray900

@SuppressLint("DefaultLocale")
@Composable
fun StopwatchScreen(
    isRunning: Boolean,
    onBackNavClicked: () -> Unit = {}
) {
    var timeInSeconds by remember { mutableStateOf(0) }
    val time = String.format("%02d:%02d", timeInSeconds / 60, timeInSeconds % 60)

    LaunchedEffect(isRunning) {
        while (isRunning) {
            kotlinx.coroutines.delay(1000L)
            timeInSeconds++
        }
    }

    AppBarStopWatch(
        isRunning = isRunning,
        time = time,
        onBackNavClicked = onBackNavClicked
    )

}

@Composable
fun AppBarStopWatch(
    isRunning: Boolean,
    time: String,
    onBackNavClicked: () -> Unit = {}
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        contentColor = WalkOneGray900,
        elevation = 4.dp,
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 80.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = if (isRunning) Color.Red else WalkOneGray600,
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(text = time,color = Color.White)
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBackNavClicked() }) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "뒤로 가기", tint = Color.White)
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun previewW() {
    StopwatchScreen(false)
}
