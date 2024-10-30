package com.ssafy.workalone.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.component.AppBarView
import com.ssafy.workalone.presentation.ui.component.Calendar
import com.ssafy.workalone.presentation.ui.component.CustomButton
import com.ssafy.workalone.presentation.ui.theme.LocalWorkAloneTypography
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme

@Composable
fun HomeView(navController: NavController, userName: String = "아무개") {
    WorkAloneTheme {
        val typography = LocalWorkAloneTypography.current
        Scaffold(
            topBar = {
                AppBarView("HOME")
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {

                Text(
                    "${userName}님, 반갑습니다!",
                    style = typography.Heading01,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.padding(32.dp))
                Calendar()
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

