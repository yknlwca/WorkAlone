package com.ssafy.workalone.presentation.ui.screen.complete

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.workalone.R
import com.ssafy.workalone.data.local.ExerciseInfoPreferenceManager
import com.ssafy.workalone.data.local.MemberPreferenceManager
import com.ssafy.workalone.data.local.SettingsPreferenceManager
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.component.ExerciseRecordDetail
import com.ssafy.workalone.presentation.ui.component.bottombar.CustomButton
import com.ssafy.workalone.presentation.ui.component.complete.ConfettiAnimation
import com.ssafy.workalone.presentation.ui.component.complete.IntegratedExerciseRecord
import com.ssafy.workalone.presentation.ui.component.topbar.CloseButton
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue100
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray300
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.viewmodels.video.ResultViewModel
import kotlin.math.roundToInt

data class IntegratedExerciseRecordData(
    val title: String,
    val setCount: Int,
    val exerciseCount: Int,
    val exerciseDuration: Int,
    val calorie: Int
)

// 운동 완료 화면(통합형)
@Composable
fun IntegratedCompleteView(
    navController: NavController,
    resultViewModel: ResultViewModel = viewModel()
) {
    val context = LocalContext.current
    val exerciseManager = ExerciseInfoPreferenceManager(context)
    val settingManager = SettingsPreferenceManager(context)
    val memberManager = MemberPreferenceManager(context)
    val videoUri = Uri.parse(exerciseManager.getFileUrl())
    val resultList = exerciseManager.getExerciseResult()
    val uploadInProgress = remember { mutableStateOf(false) }
    val awsUrl by resultViewModel.awsUrl.collectAsState()
    val exerciseData = exerciseManager.getExerciseList()
    val weight = memberManager.getWeight()
    val integratedExerciseRecordDataList= resultList.result.mapIndexed{index, result ->
        val exerciseInfo = exerciseData.getOrNull(index)
        val exerciseDuration = convertTimeToSeconds(result.time)
        val calorie = when (result.exerciseType) {
            "스쿼트" -> (6.0 * weight * (exerciseDuration / 3600.0)).roundToInt()
            "푸쉬업" -> (4.0 * weight * (exerciseDuration / 3600.0)).roundToInt()
            "윗몸 일으키기" -> (4.0 * weight *(exerciseDuration / 3600.0)).roundToInt()
            "플랭크" -> (3.0 * weight *(exerciseDuration / 3600.0)).roundToInt()
            else -> 0
        }
        IntegratedExerciseRecordData(
            title = result.exerciseType,
            setCount = exerciseInfo?.exerciseSet ?: 0,
            exerciseCount = exerciseInfo?.exerciseRepeat ?: 0,
            exerciseDuration = exerciseDuration,
            calorie = calorie
        )
    }
    val totalTime = integratedExerciseRecordDataList.sumOf { it.exerciseDuration }
    val totalCalorie = integratedExerciseRecordDataList.sumOf { it.calorie }


    //전체 화면
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WalkOneGray300)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            // 운동 인증 완료 문구 & 이미지
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = WalkOneGray50)
                    .padding(25.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    CloseButton(navController)
                }

                Text(
                    text = "운동 인증 완료!",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.work),
                    contentDescription = "Workout Complete Icon",
                    modifier = Modifier.size(120.dp)
                )
            }

            //운동 기록
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = WalkOneGray50)
                    .padding(25.dp),
            ) {
                Text(
                    text = "운동 기록",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                // 운동 기록 표
                IntegratedExerciseRecord(
                    totalTime,
                    totalCalorie
                )
            }

            //기록 상세
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = WalkOneGray50)
                    .padding(25.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                //기록 상세 문구 및 표
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 20.dp),
                ) {
                    Column {
                        Text(
                            text = "기록 상세",
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                        )
                        LazyColumn {
                            // 기록 상세 표
                            items(integratedExerciseRecordDataList) { record ->
                                Column {
                                    ExerciseRecordDetail(
                                        record.title,
                                        record.setCount,
                                        record.exerciseCount,
                                        record.exerciseDuration,
                                        record.calorie
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Divider(
                                        color = WalkOneGray300,
                                        thickness = 1.dp,
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))
                                }

                            }
                        }
                    }
                }
                Column {
                    if (settingManager.getRecordingMode()) {
                        CustomButton(
                            text = if (uploadInProgress.value) "업로드 완료"
                            else "운동 영상 업로드",
                            backgroundColor = WalkOneBlue500,
                            borderColor = if(uploadInProgress.value) WalkOneBlue100 else WalkOneBlue500,
                            enabled = !uploadInProgress.value,
                            onClick = {
                                uploadInProgress.value = true
                                resultViewModel.getAwsUrlRequest()
                                Log.d("File Uri", "$videoUri")
                            },
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    //확인 버튼
                    CustomButton(
                        text = "확인",
                        onClick = {
                            resultViewModel.sendExerciseResult(resultList, weight)
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
        ConfettiAnimation()
    }
    LaunchedEffect(awsUrl) {
        val file = getFileFromContentUri(videoUri, context)
        awsUrl?.let {
            if (file != null) {
                resultViewModel.uploadVideo(it.preSignedUrl, file)
                val rowsDeleted = context.contentResolver.delete(videoUri, null, null)
                if (rowsDeleted > 0) {
                    Log.d("File Delete", "Success")
                } else {
                    Log.d("File Delete", "Fail")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIntegratedCompleteView() {
    IntegratedCompleteView(rememberNavController())
}


fun convertTimeToSeconds(time: String): Int {
    val parts = time.split(":")
    val minutes = parts[0].toInt()
    val seconds = parts[1].toInt()
    return (minutes * 60) + seconds
}
