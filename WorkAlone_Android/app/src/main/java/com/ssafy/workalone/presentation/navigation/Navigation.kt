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
import com.ssafy.workalone.data.local.MemberPreferenceManager
import com.ssafy.workalone.mlkit.java.CameraXLivePreviewActivity
import com.ssafy.workalone.presentation.ui.screen.ExerciseDetailView
import com.ssafy.workalone.presentation.ui.screen.ExerciseListView
import com.ssafy.workalone.presentation.ui.screen.HomeView
import com.ssafy.workalone.presentation.ui.screen.IndividualCompleteView
import com.ssafy.workalone.presentation.ui.screen.LoginView
import com.ssafy.workalone.presentation.viewmodels.ExerciseViewModel

@Composable
fun Navigation(
    viewModel: ExerciseViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
) {
    val activity = LocalContext.current as? Activity
    val context = LocalContext.current
    val memberPreference = remember { MemberPreferenceManager(context) }


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
            HomeView(navController, memberPreference.getMember())
        }

        composable(Screen.Login.route) {
            LoginView(navController)
        }

        composable(Screen.ExerciseList.route) {
            ExerciseListView(navController, viewModel)
        }

        composable(
            Screen.ExerciseDetail.route,
            arguments = listOf(
                navArgument("exerciseId") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                },
                navArgument("exerciseType") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            val exerciseId =
                if (entry.arguments != null) entry.arguments!!.getLong("exerciseId") else 0L
            val exerciseType =
                if (entry.arguments != null) entry.arguments!!.getString("exerciseType") else ""
            if (exerciseType != null) {
                ExerciseDetailView(
                    navController = navController,
                    viewModel,
                    exerciseId,
                    exerciseType
                )
            }
        }

        composable(Screen.IndividualComplete.route) {
            IndividualCompleteView(
                navController = navController
            )
        }


    }
}