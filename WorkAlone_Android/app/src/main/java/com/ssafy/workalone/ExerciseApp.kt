package com.ssafy.workalone

import android.app.Application
import android.provider.Settings.Global
import com.ssafy.workalone.presentation.navigation.Graph
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExerciseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)

        // 더미 데이터 넣기
        GlobalScope.launch {
            Graph.exerciseRepository.addDummyData()
        }
    }
}