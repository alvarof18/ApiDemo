package com.alvaro.apidemo.interfaces

import com.alvaro.apidemo.models.DogsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIServices {
    @GET
    suspend fun getDogsByBreeds(@Url url:String): Response<DogsResponse>
}