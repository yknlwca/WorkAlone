package com.ssafy.workalone.presentation.viewmodels.member

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ssafy.workalone.data.local.MemberPreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MemberViewModel(context: Context) : ViewModel() {
    private val memberPreferenceManager = MemberPreferenceManager(context)
    private val _name = MutableStateFlow(memberPreferenceManager.getName() ?: "")
    val name: StateFlow<String> = _name

    private val _weight = MutableStateFlow(memberPreferenceManager.getWeight())
    val weight: StateFlow<Int> = _weight

    private val _login = MutableStateFlow(memberPreferenceManager.getLogin())
    val login: StateFlow<Boolean> = _login

    fun saveUserInfo(id: Long, name: String, weight: Int) {
        memberPreferenceManager.setMemberInfo(id, name, weight)
        _name.value = name
        _weight.value = weight
    }
}