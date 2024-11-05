package com.ssafy.workalone.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.workalone.R
import com.ssafy.workalone.presentation.navigation.Screen

@Composable
fun CloseButton(navController: NavController) {
    Icon(
        painter = painterResource(id = R.drawable.baseline_close_28),
        contentDescription = "Close",
        modifier = Modifier
            .size(24.dp)
            .clickable {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
    )
}