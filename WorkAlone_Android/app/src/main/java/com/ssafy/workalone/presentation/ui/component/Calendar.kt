package com.ssafy.workalone.presentation.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue100
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue600
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue800
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.week.DefaultDaysOfWeekHeader
import java.time.DayOfWeek
import java.time.LocalDate

@SuppressLint("NewApi")
@Composable
fun Calendar(
    markedDates: List<LocalDate> = listOf() // 운동 기록 표시할 날짜 목록
) {
    // 단일 선택 달력
    val calendarState = rememberSelectableCalendarState(
        initialSelectionMode = SelectionMode.Single,
    )
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        SelectableCalendar(
            calendarState = calendarState,
            dayContent = { dayState ->
                CustomDay(
                    state = dayState,
                    markedDates = markedDates,
                    onClick = {
                        selectedDate = it
                        // 해당 날짜 운동 호출 Default = LocalDate.now()
                    }
                )
            },
            monthHeader = { monthState ->
                CustomMonthHeader(monthState)
            },
            daysOfWeekHeader = { daysOfWeek ->
                DefaultDaysOfWeekHeader(daysOfWeek)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = selectedDate?.let { "선택한 날짜: ${it.year}년 ${it.monthValue}월 ${it.dayOfMonth}일" }
                ?: "선택한 날짜: ",
        )
        ExerciseRecordDetail(title = "스쿼트", "3 세트", 1000, 500)
        ExerciseRecordDetail(title = "푸쉬업", "2 세트", 500, 600)

    }
}

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
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = "${monthState.currentMonth.year}년 ${monthState.currentMonth.monthValue}월",
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@SuppressLint("NewApi")
@Composable
fun <T : SelectionState> CustomDay(
    state: DayState<T>,
    modifier: Modifier = Modifier,
    selectionColor: Color = WalkOneBlue500,
    currentDayColor: Color = WalkOneBlue600,
    markedDates: List<LocalDate> = listOf(),
    onClick: (LocalDate) -> Unit = {}
) {
    val date = state.date
    val selectionState = state.selectionState
    val isSelected = selectionState.isDateSelected(date)
    val isMarked = markedDates.contains(date)
    val isToday = date == LocalDate.now()
    val isCurrentMonth = state.isFromCurrentMonth

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = if (isCurrentMonth) 4.dp else 0.dp,
        border = if (isToday) BorderStroke(3.dp, currentDayColor.copy(0.5f)) else null,
        backgroundColor = when {
            isSelected -> selectionColor
            isMarked -> WalkOneBlue100
            else -> MaterialTheme.colorScheme.surface
        },
        contentColor = if (isSelected) Color.White else Color.Black
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick(date)
                    selectionState.onDateSelected(date)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                color = when {
                    isSelected -> Color.White
                    !isCurrentMonth -> Color.Gray.copy(alpha = 0.5f)
                    date.dayOfWeek == DayOfWeek.SUNDAY -> Color.Red
                    date.dayOfWeek == DayOfWeek.SATURDAY -> Color.Blue
                    else -> Color.Black
                },
                textAlign = TextAlign.Center
            )
        }
    }
}

@SuppressLint("NewApi")
@Preview(showBackground = true)
@Composable
fun a() {
    Calendar(
        markedDates = listOf(
            LocalDate.of(2024, 10, 5),
            LocalDate.of(2024, 10, 10),
            LocalDate.of(2024, 10, 15),
            LocalDate.of(2024, 10, 20)
        )
    )
}
