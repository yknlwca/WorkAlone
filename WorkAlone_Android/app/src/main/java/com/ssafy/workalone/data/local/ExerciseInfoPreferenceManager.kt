package com.ssafy.workalone.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.ssafy.workalone.data.model.exercise.ExerciseData
import com.ssafy.workalone.data.model.result.AwsUrl
import com.ssafy.workalone.data.model.result.ResultList

class ExerciseInfoPreferenceManager(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("exercise_info_preference", Context.MODE_PRIVATE)
    private val gson = Gson()

    // 운동 간 쉬는 시간 저장
    fun setRestBtwExercise(restTime: Int) {
        preferences.edit().putInt("rest_btw_exercise", restTime).apply()
    }


    fun setExerciseList(exercises: List<ExerciseData>) {
        val json = gson.toJson(exercises) // 리스트를 JSON으로 변환
        preferences.edit().putString("exercise_list", json).apply()
    }

    // 운동 간 쉬는 시간 가져오기
    fun getRestBtwExercise(): Int {
        return preferences.getInt("rest_btw_exercise", 0)
    }

    // 운동 리스트 가져오기
    fun getExerciseList(): List<ExerciseData> {
        val json = preferences.getString("exercise_list", null) ?: return emptyList()
        val type = object : TypeToken<List<ExerciseData>>() {}.type
        return gson.fromJson(json, type)
    }

    fun setAWSUrl(preSignedUrl: AwsUrl){
        val json = gson.toJson(preSignedUrl)
        preferences.edit().putString("preSigned_url", json).apply()
    }

    fun getAWSUrl() : AwsUrl{
        val json = preferences.getString("preSigned_url", null) ?: return AwsUrl()
        val type = object : TypeToken<AwsUrl>() {}.type
        return gson.fromJson(json, type)
    }

    fun setFileUrl(fileUrl: String){
        preferences.edit().putString("file_url", fileUrl).apply()
    }

    fun getFileUrl() : String{
        return preferences.getString("file_url", null) ?: return ""
    }

    // 운동이 끝나면 삭제
    fun clearAll() {
        preferences.edit().clear().apply()
    }

    // 운동 결과 저장
    fun setExerciseResult(result: ResultList) {
        val json = gson.toJson(result) // 리스트를 JSON으로 변환
        preferences.edit().putString("exercise_result", json).apply()
    }

    //운동 결과 가져오기
    fun getExerciseResult(): ResultList {
        val json = preferences.getString("exercise_result", null)
        val type = object : TypeToken<ResultList>() {}.type
        return gson.fromJson(json, type)
    }
}