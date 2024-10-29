package com.ssafy.workalone.presentation.ui.component

import android.view.View
import android.widget.Button
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500

@Composable
fun YouTubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner
) {
    var isFullScreen by remember { mutableStateOf(false) }

    AndroidView(
        modifier = if (isFullScreen) {
            Modifier.fillMaxSize()
        } else {
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 5.dp,
                    color = WalkOneBlue500,
                    shape = RoundedCornerShape(10.dp)
                )
        },
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                val iFramePlayerOptions = IFramePlayerOptions.Builder()
                    .controls(1)
                    .fullscreen(1)
                    .build()

                enableAutomaticInitialization = false

                initialize(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(youtubeVideoId, 0f)

                        val enterFullscreenButton = Button(context).apply {
                            text = "Toggle Fullscreen"
                            setOnClickListener {
                                youTubePlayer.toggleFullscreen()
                            }
                        }
                    }
                }, iFramePlayerOptions)

                // TODO 전체 화면 애뮬에선 안되는데 핸드폰에선 된다고 함.
                addFullscreenListener(object : FullscreenListener {
                    override fun onEnterFullscreen(
                        fullscreenView: View,
                        exitFullscreen: () -> Unit
                    ) {
                        isFullScreen = true
                        this@apply.visibility = View.GONE
                    }

                    override fun onExitFullscreen() {
                        isFullScreen = false
                        this@apply.visibility = View.VISIBLE
                    }
                })
            }
        }
    )
}

// https://github.com/PierfrancescoSoffritti/android-youtube-player/blob/master/core-sample-app/src/main/java/com/pierfrancescosoffritti/androidyoutubeplayer/core/sampleapp/examples/fullscreenExample/FullscreenExampleActivity.kt
