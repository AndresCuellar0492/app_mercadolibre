package com.example.appmeli.api

import com.example.appmeli.model.DataMeli
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SimpleApi {
    @GET("{search}")
    suspend fun getPost(
        @Path("search",encoded = false) search:String,
        @Query("q") name:String
    ): Response<DataMeli>

}