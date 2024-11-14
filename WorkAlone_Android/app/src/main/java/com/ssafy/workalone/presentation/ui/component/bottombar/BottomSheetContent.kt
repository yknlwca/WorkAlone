package com.ssafy.workalone.presentation.ui.component.bottombar

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ssafy.workalone.data.local.MemberPreferenceManager
import com.ssafy.workalone.data.local.SettingsPreferenceManager
import com.ssafy.workalone.data.model.member.SaveRecording
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray700
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray900
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme
import com.ssafy.workalone.presentation.viewmodels.member.MemberViewModel

@Composable
fun BottomSheetContent(
    navController: NavController, memberViewModel: MemberViewModel = viewModel()
) {
    val context = LocalContext.current
    val settingsPreference = remember { SettingsPreferenceManager(context) }
    val memberReference = remember { MemberPreferenceManager(context) }
    var isRecordingEnabled by remember { mutableStateOf(settingsPreference.getRecordingMode()) }
    val isRecording by memberViewModel.isRecording.collectAsState()

    LaunchedEffect(isRecording) {
        isRecordingEnabled = isRecording
        settingsPreference.setRecordingMode(isRecording)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "설정",
            style = WorkAloneTheme.typography.Heading01,
            color = WalkOneGray900,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 녹음 설정 스위치
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "녹화 설정",
                style = WorkAloneTheme.typography.Body01,
                color = WalkOneGray700
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isRecordingEnabled,
                onCheckedChange = { enabled ->
                    if (enabled) {
                        isRecordingEnabled = true
                        memberViewModel.saveRecordingStatus(SaveRecording(memberReference.getId(), true))
                        Toast.makeText(context, "녹화 기능이 활성화되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        isRecordingEnabled = false
                        memberViewModel.saveRecordingStatus(SaveRecording(memberReference.getId(), false))
                        Toast.makeText(context, "녹화 기능이 비활성화되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.scale(1.5f),
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Red,
                    checkedTrackColor = Color.Red.copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // 로그아웃 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    memberReference.clear()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "로그아웃",
                style = WorkAloneTheme.typography.Body01,
                color = WalkOneGray700
            )
        }
    }
}

