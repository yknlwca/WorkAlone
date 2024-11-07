package com.ssafy.workalone.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home-screen")
    object Login : Screen("login")
    object ExerciseList : Screen("exercise-list")
    object ExerciseDetail : Screen("exercise-detail/{groupId}") {
        fun createRoute(groupId: Long) = "exercise-detail/$groupId"
    }

    object IndividualComplete : Screen("individual-complete")
    object IntegratedComplete : Screen("integrated-complete")
    object LivePreviewActivity : Screen("live-preview")
}