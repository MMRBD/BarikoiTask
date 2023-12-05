package com.mmrbd.barikoitask.data.remote

import com.mmrbd.barikoitask.data.model.NearByResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BariKoiApi {
    @GET("search/nearby/0.5/1000")
    suspend fun getNearByData(
        @Query("api_key") apiKey: String,
        @Query("latitude") lat: String,
        @Query("longitude") long: String,
    ): Response<NearByResponse>
}