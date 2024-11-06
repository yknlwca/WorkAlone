package com.ssafy.workalone.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.ssafy.workalone.data.model.Member

class MemberPreferenceManager(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("member_preference", Context.MODE_PRIVATE)
    private val gson = Gson();

    // 사용자 저장
    fun setMember(member: Member) {
        val memberJson = gson.toJson(member)
        preferences.edit().putString("member_data", memberJson).apply()
    }

    // 사용자 가져오기
    fun getMember(): Member {
        val memberJson = preferences.getString("member_data", null)
        return if (memberJson != null) {
            gson.fromJson(memberJson, Member::class.java)
        } else {
            Member()
        }
    }
}