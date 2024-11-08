package com.ssafy.workalone.data.remote

import com.ssafy.workalone.data.model.Challenge
import com.ssafy.workalone.data.model.Exercise
import com.ssafy.workalone.data.model.ExerciseRecord
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExerciseService {
    @GET("/exercises")
    suspend fun getChallenges(): Response<List<Challenge>>

    // 운동 가이드 호출
    @GET("/exercises/{exerciseId}")
    suspend fun getExercise(
        @Path("exerciseId") exerciseId: Long,
    ): Response<List<Exercise>>

    @GET("/summary")
    suspend fun getExerciseRecords(
        @Query("date") date: String,
    ):Response<ExerciseRecord>
}