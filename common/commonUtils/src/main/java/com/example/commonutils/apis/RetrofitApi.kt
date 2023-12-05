package com.example.commonutils.apis

import com.example.commonutils.model.DemoModel
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitApi {
    @GET("end_point")
    suspend fun getData(): Response<DemoModel>

}