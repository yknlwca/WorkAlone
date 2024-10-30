package com.ssafy.workalone.presentation.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ssafy.workalone.presentation.ui.screen.CompleteView
import com.ssafy.workalone.presentation.ui.screen.ExerciseDetailView
import com.ssafy.workalone.presentation.ui.screen.ExerciseListView
import com.ssafy.workalone.presentation.ui.screen.HomeView
import com.ssafy.workalone.presentation.viewmodels.ExerciseViewModel

@Composable
fun Navigation(
    viewModel: ExerciseViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val activity = LocalContext.current as? Activity
    BackHandler(enabled = true) {
        if (!navController.popBackStack()) {
            activity?.finish()
        }
    }

    NavHost(
        navController = navController,
//        startDestination = Screen.ExerciseList.route
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route){
            HomeView(navController)
        }

        composable(Screen.ExerciseList.route) {
            ExerciseListView(navController, viewModel)
        }

        composable(
            Screen.ExerciseDetail.route, arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                })
        ) { entry ->
            val id = if (entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            ExerciseDetailView(navController = navController, viewModel, id)
        }

        composable(Screen.CompleteView.route) {
            CompleteView(
//                navController = navController
            )
        }
    }
}