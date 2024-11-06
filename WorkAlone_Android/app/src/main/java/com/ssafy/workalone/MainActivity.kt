package com.ssafy.workalone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ssafy.workalone.presentation.navigation.Navigation
import com.ssafy.workalone.presentation.ui.component.ConfettiAnimation
import com.ssafy.workalone.presentation.ui.component.ExerciseTimer
import com.ssafy.workalone.presentation.ui.component.RepCounter
import com.ssafy.workalone.presentation.ui.component.RestTime
import com.ssafy.workalone.presentation.ui.screen.IndividualCompleteView
import com.ssafy.workalone.presentation.ui.screen.IntegratedCompleteView
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkAloneTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Navigation()
                }
            }
        }
    }
}
