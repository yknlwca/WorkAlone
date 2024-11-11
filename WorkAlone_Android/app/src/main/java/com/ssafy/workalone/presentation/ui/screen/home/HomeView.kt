package com.ssafy.workalone.presentation.ui.screen.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ssafy.workalone.mlkit.java.CameraXLivePreviewActivity
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.component.bottombar.BottomSheetContent
import com.ssafy.workalone.presentation.ui.component.bottombar.CustomButton
import com.ssafy.workalone.presentation.ui.component.calendar.Calendar
import com.ssafy.workalone.presentation.ui.component.topbar.AppBarView
import com.ssafy.workalone.presentation.ui.theme.LocalWorkAloneTypography
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray900
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme
import com.ssafy.workalone.presentation.viewmodels.CalendarViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("NewApi")
@Composable
fun HomeView(navController: NavController, name: String) {
    WorkAloneTheme {
        val typography = LocalWorkAloneTypography.current
        val viewModel: CalendarViewModel = viewModel()
        val context = LocalContext.current
        var backPressedTime by remember { mutableStateOf(0L) }
        val coroutineScope = rememberCoroutineScope()
        // BottomSheet 상태 관리
        val modalBottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = false
        )
//        var showWeightDialog by remember { mutableStateOf(true) }
//        var userWeight by remember { mutableStateOf(70) } // 기본 몸무게

//        if (showWeightDialog) {
//            WeightPickerDialog(
//                initialWeight = userWeight,
//                onConfirm = { selectedWeight ->
//                    userWeight = selectedWeight
//                },
//                onDismiss = { showWeightDialog = false }
//            )
//        }
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
                        .padding(it),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "${name}님, 반갑습니다!",
                            style = typography.Heading01.copy(
                                color = WalkOneGray900,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 24.dp),
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
                            Calendar(viewModel)
                        }
                    }


                    Spacer(modifier = Modifier.height(24.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
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
