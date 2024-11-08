package com.ssafy.workalone.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class MemberPreferenceManager(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("member_preference", Context.MODE_PRIVATE)
    private val gson = Gson();

    fun setName(name: String) {
        preferences.edit().putString("name", name).apply()
    }

    fun getName(): String {
        return preferences.getString("name", "") ?: ""
    }

    fun setId(id: Long) {
        preferences.edit().putLong("id", id).apply()
    }

    fun getId(): Long {
        return preferences.getLong("id", 0L) ?: 0L
    }

    fun setWeight(weight: Int) {
        preferences.edit().putInt("weight", weight).apply()
    }

    fun getWeight(): Int {
        return preferences.getInt("weight", 0)
    }
}