package com.ssafy.workalone.data.remote

import com.ssafy.workalone.data.model.exercise.Challenge
import com.ssafy.workalone.data.model.exercise.Exercise
import com.ssafy.workalone.data.model.exercise.ExerciseSummary
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
    suspend fun getExerciseSummary(
        @Query("date") date: String,
    ):Response<ExerciseSummary>

    @GET("/summary/date-list")
    suspend fun getCompletedExerciseDate(): Response<List<String>>
}