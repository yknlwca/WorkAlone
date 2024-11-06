package com.ssafy.workalone.data.local

import android.content.Context
import android.content.SharedPreferences

class SettingsPreferenceManager(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("settings_preference", Context.MODE_PRIVATE)

    // 녹화 모드 ON/OFF 저장
    fun setRecordingMode(isEnabled: Boolean) {
        preferences.edit().putBoolean("recording_mode", isEnabled).apply()
    }

    // 녹화 모드 ON/OFF 가져오기
    fun getRecordingMode(): Boolean {
        return preferences.getBoolean("recording_mode", false)
    }
}