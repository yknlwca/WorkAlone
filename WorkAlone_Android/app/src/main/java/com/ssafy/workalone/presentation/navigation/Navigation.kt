package com.ssafy.workalone.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ssafy.workalone.presentation.ui.screen.ExerciseDetailScreen
import com.ssafy.workalone.presentation.ui.screen.ExerciseListScreen
import com.ssafy.workalone.presentation.viewmodels.ExerciseViewModel

@Composable
fun Navigation(
    viewModel: ExerciseViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ExerciseList.route
    ) {
        composable(Screen.ExerciseList.route) {
            ExerciseListScreen(navController, viewModel)
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
            ExerciseDetailScreen( navController = navController, viewModel, id)
        }
    }
}