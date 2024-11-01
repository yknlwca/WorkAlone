package com.ssafy.workalone.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home-screen")
    object ExerciseList : Screen("exercise-list")
    object ExerciseDetail : Screen("exercise-detail/{exerciseId}/{exerciseType}") {
        fun createRoute(exerciseId: Long, exerciseType: String) =
            "exercise-detail/$exerciseId/$exerciseType"
    }

    object IndividualComplete : Screen("individual-complete")
    object IntegratedComplete : Screen("integrated-complete")
}