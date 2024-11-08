package com.ssafy.workalone.presentation.ui.component.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.workalone.R
import com.ssafy.workalone.presentation.navigation.Screen


@Composable
fun LoginBottomView(navController: NavController) {
    val context = LocalContext.current

    Image(
        painter = painterResource(id = R.drawable.kakao_login_large_narrow),
        contentDescription = "Kakao Login",
        modifier = Modifier
            .clickable { startKakaoLogin(context, navController) }
            .scale(1.5f)
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            LoginInfoText("위 버튼을 누르면 ", TextDecoration.None)
            LoginInfoText("서비스 이용약관", TextDecoration.Underline)
            LoginInfoText("과", TextDecoration.None)
        }
        Row {
            LoginInfoText("개인정보처리방침", TextDecoration.Underline)
            LoginInfoText("에 ", TextDecoration.None)
            LoginInfoText("동의하시게 됩니다.", TextDecoration.None)
        }
    }
}

// 카카오 로그인 로직 함수
private fun startKakaoLogin(context: Context, navController: NavController) {
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("Login", "로그인 실패", error)
        } else if (token != null) {
            Log.i("Login", "로그인 성공 ${token.accessToken}")
            navController.navigate(Screen.Home.route)
        }
    }

// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e("Login", "로그인 실패", error)

                // 취소한 경우
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }

                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            } else if (token != null) {
                Log.i("Login", "로그인 성공 ${token.accessToken}")
                navController.navigate(Screen.Home.route)
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }
}