package com.ssafy.workalone.presentation.ui.component.Calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.workalone.presentation.ui.component.ExerciseRecordDetail
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue100
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue600
import com.ssafy.workalone.presentation.viewmodels.CalendarViewModel
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.week.DefaultDaysOfWeekHeader
import java.time.DayOfWeek
import java.time.LocalDate

@SuppressLint("NewApi")
@Composable
fun Calendar(
    viewModel: CalendarViewModel
) {
    val calendarState = rememberSelectableCalendarState(initialSelectionMode = SelectionMode.Single)
    val selectedDate = viewModel.selectedDate
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)) {
        SelectableCalendar(
            calendarState = calendarState,
            dayContent = { dayState ->
                CustomDay(
                    state = dayState,
                    onClick = {
                        viewModel.changeSelectedDate(it)
                    },
                    viewModel = viewModel
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
            text = selectedDate.value?.let { "${it.monthValue} / ${it.dayOfMonth}" }
                ?: "선택된 날짜가 없습니다.",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,

        )
        ExerciseRecordDetail(title = "스쿼트", "3 세트", 1000, 500)
        ExerciseRecordDetail(title = "푸쉬업", "2 세트", 500, 600)

    }
}



@SuppressLint("NewApi")
@Composable
fun <T : SelectionState> CustomDay(
    state: DayState<T>,
    selectionColor: Color = WalkOneBlue500,
    currentDayColor: Color = WalkOneBlue600,
    onClick: (LocalDate) -> Unit = {},
    viewModel: CalendarViewModel
) {
    val date = state.date
    val selectionState = state.selectionState
    val isSelected = viewModel.selectedDate.value == date
    val isMarked = viewModel.markedDates.contains(date)
    val isToday = date == LocalDate.now()
    val isCurrentMonth = state.isFromCurrentMonth

    Card(
        modifier = Modifier
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
    // 단일 선택 달력
    Calendar(
        viewModel = viewModel()
    )
}
