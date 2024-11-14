package com.ssafy.workalone.presentation.viewmodels.member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.workalone.data.model.member.Member
import com.ssafy.workalone.data.model.member.SaveMember
import com.ssafy.workalone.data.model.member.SaveRecording
import com.ssafy.workalone.data.repository.MemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MemberViewModel(
    private val memberRepository: MemberRepository = MemberRepository()
) : ViewModel() {
    private var _member = MutableStateFlow<Member?>(null)
    val member: StateFlow<Member?> = _member

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name
    private val _weight = MutableStateFlow(0)
    val weight: StateFlow<Int> = _weight

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording

    fun setMember(memberId: Long) {
        viewModelScope.launch {
            memberRepository.getMember(memberId).collect { member ->
                _member.value = member
                _name.value = member.memberName
                _weight.value = member.memberWeight
                _isRecording.value = member.isRecording
            }
        }
    }

    fun registerMember(member: SaveMember){
        viewModelScope.launch {
            memberRepository.registerMember(member).collect { member ->
                _member.value = member
            }
        }
    }

    fun saveRecordingStatus(saveRecording: SaveRecording) {
        viewModelScope.launch {
            memberRepository.saveRecordingStatus(saveRecording).collect { member ->
                _member.value = member
                _isRecording.value = member.isRecording
            }
        }
    }
}