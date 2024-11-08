package com.ssafy.workalone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.workalone.presentation.navigation.Navigation
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        val startDestination = intent.getStringExtra("startDestination") ?: Screen.Home.route
        setContent {
            WorkAloneTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Navigation(startDestination = startDestination)
//                    AWSS3UploadScreen()
                }
            }
        }
    }
}
