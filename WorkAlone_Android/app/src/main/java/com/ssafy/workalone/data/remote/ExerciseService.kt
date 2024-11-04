package com.ssafy.workalone.data.remote

import com.ssafy.workalone.data.model.Exercise
import com.ssafy.workalone.data.model.ExerciseRecord
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.POST

interface ExerciseService {
    @GET("/exercises")
    suspend fun getExercises(): Response<List<Exercise>>

    // 운동 가이드 호출
    @GET("/exercises/{exerciseId}")
    suspend fun getExercise(
        @Path("exerciseId") exerciseId: Long,
        @Query("exercise-type") exerciseType: String
    ): Response<List<Exercise>>

    @POST("/exercises")
    suspend fun getExerciseRecords(): Response<List<ExerciseRecord>>
}