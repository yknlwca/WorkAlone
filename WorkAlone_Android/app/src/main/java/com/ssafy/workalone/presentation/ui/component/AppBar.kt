package com.ssafy.workalone.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.workalone.R
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray900
import kotlinx.coroutines.launch

@Composable
fun AppBarView(
    title: String,
    navController: NavController? = null,
) {
    val coroutineScope = rememberCoroutineScope()
    if (!title.contains("HOME")) {
        TopAppBar(
            backgroundColor = WalkOneGray50,
            contentColor = WalkOneGray900,
            elevation = 4.dp,
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        color = WalkOneGray900,
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "뒤로 가기")
                }
            }
        )
    } else {
        //메인 페이지 앱바
        TopAppBar(
            backgroundColor = WalkOneGray50,
            title = {
                Image(
                    painter = painterResource(R.drawable.workalone),
                    contentDescription = "Walk Alone",
                    modifier = Modifier.size(200.dp)

                )
            },
            actions = {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "설정",
                        modifier = Modifier.size(36.dp)
                    )
                }
            },
            modifier = Modifier.height(70.dp)
        )
    }
}
