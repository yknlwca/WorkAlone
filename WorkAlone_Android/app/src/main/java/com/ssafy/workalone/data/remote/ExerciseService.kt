package com.ssafy.workalone.data.remote

import com.ssafy.workalone.data.model.Exercise
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExerciseService {
    @GET("/exercises")
    suspend fun getExercises(): Response<List<Exercise>>

    // 운동 가이드 호출
    @GET("/exercises/{exerciseId}")
    suspend fun getExercise(
        @Path("exerciseId") exerciseId: Long,
        @Query("exercise-type") exerciseType: String
    ): Response<List<Exercise>>
}