package com.ssafy.workalone.presentation.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ssafy.workalone.data.local.MemberPreferenceManager
<<<<<<< HEAD
import com.ssafy.workalone.presentation.ui.screen.ExerciseDetailView
import com.ssafy.workalone.presentation.ui.screen.ExerciseListView
import com.ssafy.workalone.presentation.ui.screen.HomeView
import com.ssafy.workalone.presentation.ui.screen.IndividualCompleteView
import com.ssafy.workalone.presentation.ui.screen.LoginView
=======
import com.ssafy.workalone.presentation.ui.screen.complete.IndividualCompleteView
import com.ssafy.workalone.presentation.ui.screen.exercise.ChallengeListView
import com.ssafy.workalone.presentation.ui.screen.exercise.ExerciseView
import com.ssafy.workalone.presentation.ui.screen.home.HomeView
import com.ssafy.workalone.presentation.ui.screen.home.LoginView
>>>>>>> origin/develop

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
) {
    val activity = LocalContext.current as? Activity
    val context = LocalContext.current
    val memberManager = remember { MemberPreferenceManager(context) }

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
            HomeView(navController, memberManager.getName())
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
                })
        ) { entry ->
            val exerciseId =
                if (entry.arguments != null) entry.arguments!!.getLong("groupId") else 0L
            ExerciseView(
                navController = navController,
                id = exerciseId,
            )
        }

        composable(Screen.IndividualComplete.route) {
            IndividualCompleteView(
                navController = navController
            )
        }
    }
}