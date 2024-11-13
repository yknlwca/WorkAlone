package com.ssafy.workalone.presentation.ui.component.exercise

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500

//
//@OptIn(UnstableApi::class)
//@Composable
//fun VideoPlayer(
//    viewModel: ExerciseViewModel = viewModel(),
//    videoUrl: String,
//    context: Context = LocalContext.current
//) {
//    val isFullScreen by viewModel.isFullScreen.collectAsState()
//    val playBackPosition by viewModel.playBackPosition.collectAsState()
//
//    if (isFullScreen) {
//        (context as Activity).apply {
//            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                window.setDecorFitsSystemWindows(false)
//                window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
//                window.insetsController?.systemBarsBehavior =
//                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            } else {
//                window.decorView.systemUiVisibility = (
//                        View.SYSTEM_UI_FLAG_FULLSCREEN or
//                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
//                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
//            }
//        }
//    } else {
//        (context as Activity).apply {
//            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                window.insetsController?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
//                window.setDecorFitsSystemWindows(true)
//            } else {
//                window.decorView.systemUiVisibility = View.SYSTEM_UI_LAYOUT_FLAGS
//            }
//        }
//    }
//
//    AndroidView(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .height(200.dp)
//            .clip(RoundedCornerShape(10.dp)),
//        factory = { ctx ->
//            PlayerView(ctx).apply {
//                val exoPlayer = ExoPlayer.Builder(ctx).build().apply {
//                    val mediaSource = ProgressiveMediaSource.Factory(DefaultDataSource.Factory(ctx))
//                        .createMediaSource(MediaItem.fromUri(Uri.parse(videoUrl)))
//                    setMediaSource(mediaSource)
//                    prepare()
//                    playWhenReady = true
//                    seekTo(playBackPosition ?: 0L)
//                }
//
//                player = exoPlayer
//                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
//                setFullscreenButtonClickListener {
//                    viewModel.toggleFullScreen(exoPlayer.currentPosition)
//                }
//                exoPlayer.addListener(object : Player.Listener {
//                    override fun onEvents(player: Player, events: Player.Events) {
//                        if (events.contains(Player.EVENT_POSITION_DISCONTINUITY) || events.contains(
//                                Player.EVENT_PLAYBACK_STATE_CHANGED
//                            )
//                        ) {
//                            viewModel.updatePlayBackPosition(player.currentPosition)
//                        }
//                    }
//                })
//            }
//        }
//    )
//
//    BackHandler(enabled = isFullScreen) {
//        if (isFullScreen) {
//            viewModel.toggleFullScreen(null)
//        }
//    }
//}

@Composable
fun YouTubePlayer(
    youtubeId: String,
    lifecycleOwner: LifecycleOwner
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, WalkOneBlue500, RoundedCornerShape(16.dp)),
        factory = { context ->
            YouTubePlayerView(context = context).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(youtubeId, 0f)
                    }
                })
            }
        }
    )
}

