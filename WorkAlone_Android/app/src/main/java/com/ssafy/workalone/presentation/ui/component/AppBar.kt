package com.ssafy.workalone.presentation.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray900
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme

@Composable
fun AppBarView(
    title: String,
    onBackNavClicked: () -> Unit = {}
) {
    val navigationIcon: (@Composable () -> Unit)? =
        if (!title.contains("운동 선택")) {
            {
                IconButton(onClick = { onBackNavClicked() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = WalkOneGray900,
                        modifier = Modifier
                            .size(28.dp)
                            .padding(start = 8.dp)
                    )
                }
            }
        } else {
            null
        }

    TopAppBar(
        modifier = Modifier
            .height(72.dp),
        title = {
            Text(
                text = title,
                color = WalkOneGray900,
                style = WorkAloneTheme.typography.Heading01,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .heightIn(max = 36.dp)
            )
        },
        backgroundColor = WalkOneGray50,
        contentColor = WalkOneGray900,
        elevation = 6.dp,
        navigationIcon = navigationIcon,
        actions = {
//            // 추가 메뉴 버튼
//            IconButton(onClick = { /* TODO: Add Action Logic */ }) {
//                Icon(
//                    imageVector = Icons.Default.MoreVert,
//                    contentDescription = "More actions",
//                    tint = Color.White,
//                    modifier = Modifier.size(28.dp)
//                )
//            }
        }
    )
}

