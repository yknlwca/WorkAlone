//package com.ssafy.workalone.presentation.ui.component
//
//import android.app.Activity
//import android.content.pm.ActivityInfo
//import android.view.LayoutInflater
//import android.view.View
//import androidx.activity.compose.BackHandler
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalLifecycleOwner
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.view.WindowInsetsCompat
//import androidx.core.view.WindowInsetsControllerCompat
//import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
//import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
//import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
//import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
//import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
//import com.ssafy.workalone.databinding.ActivityYouTubePlayerBinding
//import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
//
//@Composable
//fun YouTubePlayerScreen(
//    videoId: String,
//) {
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val isFullScreen = remember { mutableStateOf(false) }
//    val activity = context as? Activity
//
//    BackHandler(enabled = true) {
//        if (isFullScreen.value) {
//            isFullScreen.value = false
//            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//        } else {
//            activity?.finish()
//        }
//    }
//
//    AndroidView(
//        modifier =  Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clip(RoundedCornerShape(10.dp))
//            .border(
//                width = 4.dp,
//                color = WalkOneBlue500,
//                shape = RoundedCornerShape(10.dp)
//            )
//        ,
//        factory = { context ->
//            val binding = ActivityYouTubePlayerBinding.inflate(LayoutInflater.from(context))
//            val youTubePlayerView = binding.YoutubePlayer
//            val fullScreenContainer = binding.FullScreenYouTubePlayer
//
//            youTubePlayerView.enableAutomaticInitialization = false
//
//            val listner = object : AbstractYouTubePlayerListener() {
//                override fun onReady(youTubePlayer: YouTubePlayer) {
//                    youTubePlayer.loadOrCueVideo(lifecycleOwner.lifecycle, videoId, 0f)
//                }
//            }
//
//            val iFramePlayerOptions = IFramePlayerOptions.Builder()
//                .controls(1)
//                .fullscreen(1)
//                .build()
//
//            youTubePlayerView.initialize(listner, iFramePlayerOptions)
//
//            // FullScreen
//            youTubePlayerView.addFullscreenListener(object : FullscreenListener {
//                override fun onEnterFullscreen(
//                    fullscreenView: View,
//                    exitFullscreen: () -> Unit
//                ) {
//                    isFullScreen.value = true
//                    fullScreenContainer.visibility = View.VISIBLE
//                    fullScreenContainer.addView(fullscreenView)
//
//                    // 임시 가로 모드 전환
//                    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
//
//                    // 시스템 바 숨기기
//                    activity?.let {
//                        WindowInsetsControllerCompat(it.window, binding.rootView).apply {
//                            hide(WindowInsetsCompat.Type.systemBars())
//                            systemBarsBehavior =
//                                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//                        }
//                    }
//                }
//
//                override fun onExitFullscreen() {
//                    isFullScreen.value = false
//                    fullScreenContainer.visibility = View.GONE
//                    fullScreenContainer.removeAllViews()
//
//                    // 임시 가로 모드 해제
//                    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
//
//                    // 시스템 바 보이기
//                    activity?.let {
//                        WindowInsetsControllerCompat(it.window, binding.rootView).apply {
//                            show(WindowInsetsCompat.Type.systemBars())
//                        }
//                    }
//                }
//            })
//            binding.rootView
//        },
//    )
//}