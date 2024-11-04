package com.ssafy.workalone.presentation.ui.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ssafy.workalone.mlkit.java.LivePreviewActivity
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.component.AppBarView
import com.ssafy.workalone.presentation.ui.component.Calendar.Calendar
import com.ssafy.workalone.presentation.ui.component.CustomButton
import com.ssafy.workalone.presentation.ui.theme.LocalWorkAloneTypography
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray900
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme
import com.ssafy.workalone.presentation.viewmodels.CalendarViewModel

@SuppressLint("NewApi")
@Composable
fun HomeView(navController: NavController, userName: String = "아무개") {
    WorkAloneTheme {
        val typography = LocalWorkAloneTypography.current
        val viewModel:CalendarViewModel = viewModel()
        val context = LocalContext.current

        Scaffold(
            topBar = {
                AppBarView("HOME")
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
                        text = "${userName}님, 반갑습니다!",
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
//                            navController.navigate(Screen.ExerciseList.route)
                            val intent = Intent(context, LivePreviewActivity::class.java)
Log.d("123","여기여기")
                            context.startActivity(intent)
                        },
                    )
                }
            }
        }
    }
}


