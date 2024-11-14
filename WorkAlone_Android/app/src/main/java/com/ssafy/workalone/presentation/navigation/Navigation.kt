package com.ssafy.workalone.presentation.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ssafy.workalone.presentation.ui.screen.complete.IndividualCompleteView
import com.ssafy.workalone.presentation.ui.screen.complete.IntegratedCompleteView
import com.ssafy.workalone.presentation.ui.screen.exercise.ChallengeListView
import com.ssafy.workalone.presentation.ui.screen.exercise.ExerciseView
import com.ssafy.workalone.presentation.ui.screen.home.HomeView
import com.ssafy.workalone.presentation.ui.screen.home.LoginView
import com.ssafy.workalone.presentation.viewmodels.member.MemberViewModel

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
) {
    val activity = LocalContext.current as? Activity

    BackHandler(enabled = true) {
        if (!navController.popBackStack()) {
            activity?.finish()
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            HomeView(navController)
        }

        composable(Screen.Login.route) {
            LoginView(navController)
        }

        composable(Screen.ExerciseList.route) {
            ChallengeListView(navController)
        }

        composable(
            Screen.ExerciseDetail.route,
            arguments = listOf(
                navArgument("groupId") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                },
                navArgument("seq") {
                    type = NavType.IntType
                    defaultValue = 1
                    nullable = false
                })
        ) { entry ->
            val exerciseId =
                if (entry.arguments != null) entry.arguments!!.getLong("groupId") else 0L
            val seq =
                if (entry.arguments != null) entry.arguments!!.getInt("seq") else 1
            ExerciseView(
                navController = navController,
                id = exerciseId,
                seq = seq
            )
        }

        composable(Screen.IndividualComplete.route) {
            IndividualCompleteView(
                navController = navController
            )
        }
        composable(Screen.IntegratedComplete.route) {
            IntegratedCompleteView(
                navController = navController
            )
        }
    }
}