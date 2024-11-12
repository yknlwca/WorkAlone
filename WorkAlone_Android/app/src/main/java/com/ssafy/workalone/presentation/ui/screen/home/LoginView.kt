package com.ssafy.workalone.presentation.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ssafy.workalone.presentation.ui.component.login.LoginBottomView
import com.ssafy.workalone.presentation.ui.component.login.LoginMiddleView
import com.ssafy.workalone.presentation.viewmodels.member.MemberViewModel

@Composable
fun LoginView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LoginMiddleView()
        Spacer(modifier = Modifier.weight(1f))
        LoginBottomView(navController, MemberViewModel(LocalContext.current))
        Spacer(modifier = Modifier.weight(1f))
    }
}
