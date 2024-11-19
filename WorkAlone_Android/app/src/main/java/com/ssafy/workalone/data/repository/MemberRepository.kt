package com.ssafy.workalone.data.repository

import com.ssafy.workalone.data.model.member.Member
import com.ssafy.workalone.data.model.member.SaveMember
import com.ssafy.workalone.data.model.member.SaveRecording
import com.ssafy.workalone.data.remote.MemberService
import com.ssafy.workalone.data.remote.RetrofitFactory
import com.ssafy.workalone.global.exception.handleApiError
import com.ssafy.workalone.global.exception.handleException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MemberRepository(
    private val memberService: MemberService =
        RetrofitFactory.getInstance().create(MemberService::class.java)
) {
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage


    suspend fun getMember(id: Long): Flow<Member> = flow {
        val response = memberService.getMember(id)
        if (response.isSuccessful) {
            response.body()?.let { emit(it) }
        } else {
            handleApiError(response.code(), response.errorBody()?.string())
        }
    }

    // 멤버 등록
    fun registerMember(member: SaveMember): Flow<Member> = flow {
        val response = memberService.registerMember(member)
        if (response.isSuccessful && response.body() != null) {
            emit(response.body()!!)
        }
    }.flowOn(Dispatchers.IO).catch { e ->
        handleException(e, _errorMessage)
    }

    // 녹화 상태 저장
    fun saveRecordingStatus(saveRecording: SaveRecording): Flow<Member> = flow {
        val response = memberService.saveRecording(saveRecording)
        if (response.isSuccessful && response.body() != null) {
            emit(response.body()!!)
        }
    }.flowOn(Dispatchers.IO).catch { e ->
        handleException(e, _errorMessage)
    }
}