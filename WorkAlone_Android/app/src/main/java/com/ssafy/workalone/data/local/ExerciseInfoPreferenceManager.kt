package com.ssafy.workalone.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class ExerciseInfoPreferenceManager(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("exercise_info_preference", Context.MODE_PRIVATE)
    private val gson = Gson()

    // 운동 간 쉬는 시간 저장
    fun setRestBtwExercise(restTime: Int) {
        preferences.edit().putInt("rest_btw_exercise", restTime).apply()
    }

    // 운동 간 쉬는 시간 가져오기
    fun getRestBtwExercise(): Int {
        return preferences.getInt("rest_btw_exercise", 0)
    }

    // 운동 정보 저장
    fun setExerciseCount(
        title: String,
        restBtwSet: Int,
        exerciseSet: Int,
        exerciseRepeat: Int,
        type: String
    ) {
        preferences.edit().putString("title", title).apply()
        preferences.edit().putInt("rest_btw_set", restBtwSet).apply()
        preferences.edit().putInt("exercise_set", exerciseSet).apply()
        preferences.edit().putInt("exercise_repeat", exerciseRepeat).apply()
        preferences.edit().putString("type", type).apply()
    }

    // 세트 간 쉬는 시간 가져오기
    fun getRestBtwSet(): Int {
        return preferences.getInt("rest_btw_set", 0)
    }

    fun getType(): String {
        return preferences.getString("type", "") ?: ""
    }

    fun getTitle(): String {
        return preferences.getString("title", "") ?: ""
    }

    // 운동 세트 수 가져오기
    fun getExerciseCount(): Int {
        return preferences.getInt("exercise_set", 0)
    }


    // 운동 횟수 가져오기
    fun getExerciseRepeat(): Int {
        return preferences.getInt("exercise_repeat", 0)
    }

    // 운동이 끝나면 삭제
    fun clearAll() {
        preferences.edit().clear().apply()
    }
}