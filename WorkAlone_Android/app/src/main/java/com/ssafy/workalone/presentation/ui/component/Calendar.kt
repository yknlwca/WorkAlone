package com.ssafy.workalone.presentation.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.boguszpawlowski.composecalendar.StaticCalendar

// https://github.com/boguszpawlowski/ComposeCalendar
@Composable
fun Calendar(){
    StaticCalendar(
    )
}

@Preview(showBackground = true)
@Composable
fun a(){
    Calendar()
}