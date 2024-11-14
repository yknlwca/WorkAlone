package com.ssafy.workalone.data.remote

import com.ssafy.workalone.data.model.member.Member
import com.ssafy.workalone.data.model.member.SaveMember
import com.ssafy.workalone.data.model.member.SaveRecording
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface MemberService {

    @POST("/auth/save")
    suspend fun registerMember(
        @Body member: SaveMember
    ): Response<Member>

    @GET("/auth/read")
    suspend fun getMember(
        @Query("memberId") id: Long
    ): Response<Member>

    @PATCH("/auth/update")
    suspend fun saveRecording(
        @Body saveRecording: SaveRecording
    ):Response<Member>
}