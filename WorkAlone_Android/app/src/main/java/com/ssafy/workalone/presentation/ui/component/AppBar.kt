package com.ssafy.workalone.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.workalone.R
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray900
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AppBarView(
    title: String,
    navController: NavController? = null,
    modalBottomSheetState: ModalBottomSheetState? = null
) {
    val coroutineScope = rememberCoroutineScope()
    if (!title.contains("HOME")) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = WalkOneGray50,
            ),

            title = {
                    Text(
                        text = title,
                        color = WalkOneGray900,
                        fontWeight = FontWeight.Bold,
                    )
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
                            modalBottomSheetState?.show()
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
