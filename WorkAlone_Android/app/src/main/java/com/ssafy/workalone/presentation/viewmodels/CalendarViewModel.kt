package com.ssafy.workalone.presentation.viewmodels

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate

@SuppressLint("NewAPI")
class CalendarViewModel() : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _selectedDate: MutableState<LocalDate?> = mutableStateOf(LocalDate.now())
    private val _markedDates: MutableList<LocalDate> = mutableListOf(
        LocalDate.of(2024, 10, 5),
        LocalDate.of(2024, 10, 10),
        LocalDate.of(2024, 10, 15),
        LocalDate.of(2024, 10, 20)
    )
    val selectedDate: MutableState<LocalDate?> get() = _selectedDate
    val markedDates: MutableList<LocalDate> get() = _markedDates
    fun changeSelectedDate(date: LocalDate) {
        if (_selectedDate.value == date) {
            return
        } else {
            _selectedDate.value = date
        }
    }
}