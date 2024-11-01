package com.ssafy.workalone.presentation.ui.component.Calendar

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.boguszpawlowski.composecalendar.header.MonthState

@SuppressLint("NewApi")
@Composable
fun CustomMonthHeader(
    monthState: MonthState,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DecrementButton(monthState = monthState)
        Text(
            text = "${monthState.currentMonth.year}년 ${monthState.currentMonth.monthValue}월",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        IncrementButton(monthState = monthState)
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DecrementButton(
    monthState: MonthState,
) {
    IconButton(
        modifier = Modifier.testTag("Decrement"),
        enabled = monthState.currentMonth > monthState.minMonth,
        onClick = { monthState.currentMonth = monthState.currentMonth.minusMonths(1) }
    ) {
        Image(
            imageVector = Icons.Default.KeyboardArrowLeft,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
            contentDescription = "Previous",
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun IncrementButton(
    monthState: MonthState,
) {
    IconButton(
        modifier = Modifier.testTag("Increment"),
        enabled = monthState.currentMonth < monthState.maxMonth,
        onClick = { monthState.currentMonth = monthState.currentMonth.plusMonths(1) }
    ) {
        Image(
            imageVector = Icons.Default.KeyboardArrowRight,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
            contentDescription = "Next",
        )
    }
}