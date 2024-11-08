package com.ssafy.workalone.presentation.ui.component

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import com.ssafy.workalone.data.local.SettingsPreferenceManager
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray700
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray900
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme

@Composable
fun BottomSheetContent(
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val settingsPreference = remember { SettingsPreferenceManager(context) }
    var isRecordingEnabled by remember { mutableStateOf(settingsPreference.getRecordingMode()) }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val cameraGranted = permissions[android.Manifest.permission.CAMERA] ?: false
            val audioGranted = permissions[android.Manifest.permission.RECORD_AUDIO] ?: false
            if (cameraGranted && audioGranted) {
                Toast.makeText(context, "녹화 기능이 활성화되었습니다.", Toast.LENGTH_SHORT).show()
            }
            if (!cameraGranted) {
                Toast.makeText(context, "녹화 기능은 카메라 권한이 필요합니다", Toast.LENGTH_SHORT).show()
            }
            if (!audioGranted) {
                Toast.makeText(context, "녹화 기능은 오디오 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
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
                        // 권한이 있는지 확인
                        val hasCameraPermission = ContextCompat.checkSelfPermission(
                            context, android.Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                        val hasAudioPermission = ContextCompat.checkSelfPermission(
                            context, android.Manifest.permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED

                        if (hasCameraPermission && hasAudioPermission) {
                            // 이미 권한이 있는 경우
                            isRecordingEnabled = true
                            settingsPreference.setRecordingMode(true)
                            Toast.makeText(context, "녹화 기능이 활성화되었습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            // 권한이 없는 경우 권한 요청
                            requestPermissionLauncher.launch(
                                arrayOf(
                                    android.Manifest.permission.CAMERA,
                                    android.Manifest.permission.RECORD_AUDIO
                                )
                            )
                        }
                    } else {
                        // 스위치를 OFF로 변경할 때
                        isRecordingEnabled = false
                        settingsPreference.setRecordingMode(false)
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
                .clickable { onLogout() },
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
