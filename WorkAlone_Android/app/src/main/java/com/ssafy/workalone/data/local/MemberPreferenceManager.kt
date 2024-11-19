package com.ssafy.workalone.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class MemberPreferenceManager(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("member_preference", Context.MODE_PRIVATE)
    private val gson = Gson();


    fun getName(): String {
        return preferences.getString("name", "") ?: ""
    }

    fun setId(id: Long) {
        preferences.edit().putLong("memberId", id).apply()
    }

    fun getId(): Long {
        return preferences.getLong("memberId", 0L)
    }

    fun getWeight(): Int {
        return preferences.getInt("weight", 0)
    }

    fun setMemberInfo(id: Long, name: String, weight: Int) {
        preferences.edit().putLong("memberId", id).putString("name", name).putInt("weight", weight)
            .apply()
    }

    fun setLogin(isLogin: Boolean) {
        preferences.edit().putBoolean("Login", isLogin).apply()
    }

    fun getLogin(): Boolean {
        return preferences.getBoolean("Login", false)
    }

    // 운동이 끝나면 삭제
    fun clear() {
        preferences.edit().clear().apply()
    }
}