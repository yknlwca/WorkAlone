package com.ssafy.workalone.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home-screen")
    object Login : Screen("login")
    object ExerciseList : Screen("exercise-list")
    object ExerciseDetail : Screen("exercise-detail/{groupId}/{seq}") {
        fun createRoute(groupId: Long, seq: Int) = "exercise-detail/$groupId/$seq"
    }

    object IndividualComplete : Screen("individual-complete")
    object IntegratedComplete : Screen("integrated-complete")
    object LivePreviewActivity : Screen("live-preview")
}