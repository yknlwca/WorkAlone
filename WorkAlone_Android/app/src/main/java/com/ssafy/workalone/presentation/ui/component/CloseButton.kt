package com.ssafy.workalone.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.workalone.R
import androidx.compose.ui.Modifier

@Composable
fun CloseButton(onClick: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.baseline_close_28),
        contentDescription = "Close",
        modifier = Modifier
            .size(24.dp) // 아이콘 크기
            .clickable { onClick() }
    )
}