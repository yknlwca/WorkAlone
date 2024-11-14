package com.ssafy.workalone.presentation.ui.screen.home

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ssafy.workalone.data.local.MemberPreferenceManager
import com.ssafy.workalone.data.model.exercise.ExerciseSummary
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.component.ExerciseRecordDetail
import com.ssafy.workalone.presentation.ui.component.bottombar.BottomSheetContent
import com.ssafy.workalone.presentation.ui.component.bottombar.CustomButton
import com.ssafy.workalone.presentation.ui.component.calendar.Calendar
import com.ssafy.workalone.presentation.ui.component.topbar.AppBarView
import com.ssafy.workalone.presentation.ui.theme.LocalWorkAloneTypography
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray900
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme
import com.ssafy.workalone.presentation.viewmodels.CalendarViewModel
import com.ssafy.workalone.presentation.viewmodels.exercise.ExerciseSummaryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("NewApi")
@Composable
fun HomeView(
    navController: NavController
) {
    WorkAloneTheme {
        val context = LocalContext.current
        val calendarViewModel: CalendarViewModel = viewModel()
        val summaryViewModel: ExerciseSummaryViewModel = viewModel()
        val typography = LocalWorkAloneTypography.current
        val memberManager = MemberPreferenceManager(context)
        var backPressedTime by remember { mutableStateOf(0L) }
        val coroutineScope = rememberCoroutineScope()
        // BottomSheet 상태 관리
        val modalBottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = false
        )

        val selectedDate = calendarViewModel.selectedDate
        val summaryList = remember { mutableStateOf(
            ExerciseSummary(
                totalDuration = 0,
                totalKcal = 0,
                totalSummary = emptyList()
            )
        ) }

        LaunchedEffect(selectedDate.value) {
            summaryViewModel.getExerciseSummary(selectedDate.value.toString())
                .collect { newSummary ->
                    summaryList.value = newSummary
                }
        }

        BackHandler {
            if (System.currentTimeMillis() - backPressedTime < 2000) {
                (context as? Activity)?.finish()
            } else {
                backPressedTime = System.currentTimeMillis()
                coroutineScope.launch {
                    Toast.makeText(context, "'뒤로'버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
                    delay(2000)
                    backPressedTime = 0L
                }
            }
        }
        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            sheetBackgroundColor = WalkOneGray50,
            sheetContent = {
                BottomSheetContent(navController)
            }
        ) {
            Scaffold(
                topBar = {
                    AppBarView("HOME", modalBottomSheetState = modalBottomSheetState)
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${memberManager.getName()}님, 반갑습니다!",
                            style = typography.Heading01.copy(
                                color = WalkOneGray900,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Calendar(calendarViewModel)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            androidx.compose.material.Text(
                                text = selectedDate.value?.let { "${it.monthValue} / ${it.dayOfMonth}" }
                                    ?: "선택된 날짜가 없습니다.",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black,
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if(summaryList.value.totalSummary.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "운동 기록이 없습니다.",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    itemsIndexed(summaryList.value.totalSummary) { index, exerciseDeetails ->
                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 10.dp)
                                                .border(
                                                    width = 1.dp,
                                                    color = WalkOneBlue500,
                                                    shape = RoundedCornerShape(8.dp)
                                                ),
                                            shape = RoundedCornerShape(8.dp),
                                            elevation = 4.dp

                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp)
                                            ) {
                                                exerciseDeetails.forEachIndexed { detailIndex, exerciseDetail ->
                                                    ExerciseRecordDetail(
                                                        exerciseDetail.exerciseType,
                                                        exerciseDetail.exerciseSet,
                                                        exerciseDetail.exerciseRepeat,
                                                        exerciseDetail.exerciseDuration,
                                                        exerciseDetail.kcal
                                                    )
                                                    if (detailIndex < (exerciseDeetails.size - 1)) {
                                                        Divider(modifier = Modifier.padding(vertical = 10.dp))
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CustomButton(
                            text = "챌린지 이동하기",
                            onClick = {
                                navController.navigate(Screen.ExerciseList.route)
                            },
                        )
                    }
                }
            }
        }
    }
}
