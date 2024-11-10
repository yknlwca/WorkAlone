package com.ssafy.workalone.presentation.ui.component.complete

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

@Composable
fun ConfettiAnimation(animationDuration: Long = 8000L) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("confetti.json"))
    var isPlaying by remember { mutableStateOf(true) }
    var isVisible by remember { mutableStateOf(true) }

    var targetAlpha by remember { mutableStateOf(1f) }

    val process by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        speed = 1.5f
    )

    val alpha by animateFloatAsState(targetValue = targetAlpha)

    // 일정 시간 후 애니메이션 중지 및 숨김 처리
    LaunchedEffect(Unit) {
//        delay(animationDuration)
        delay(animationDuration-2000)
        targetAlpha = 0f // alpha를 0으로 설정하여 서서히 페이드 아웃
        delay(2000)
        isPlaying = false
        isVisible = false
    }

    if(isVisible) {
        LottieAnimation(
            composition = composition,
            progress = { process },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.9f)
                .alpha(alpha)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewConfettiAnimation(){
    ConfettiAnimation()
}