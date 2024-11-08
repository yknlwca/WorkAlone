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
import com.ssafy.workalone.data.local.MemberPreferenceManager
import com.ssafy.workalone.data.model.Member
import com.ssafy.workalone.presentation.navigation.Navigation
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.component.ExerciseTimer
import com.ssafy.workalone.presentation.ui.component.RepCounter
import com.ssafy.workalone.presentation.ui.component.RestTime
import com.ssafy.workalone.presentation.ui.screen.IndividualCompleteView
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme
import com.ssafy.workalone.presentation.viewmodels.ExerciseMLKitViewModel
import com.ssafy.workalone.presentation.viewmodels.ExerciseRecordViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 멤버 더미 데이터
        val memberPreference = MemberPreferenceManager(this)
        memberPreference.setMember(Member())

        val startDestination = intent.getStringExtra("startDestination") ?: Screen.Home.route
        setContent {
            WorkAloneTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Navigation(startDestination = startDestination)
                }
            }
        }
    }
}
