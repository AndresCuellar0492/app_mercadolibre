package com.example.appmeli.repository

import com.example.appmeli.api.RetrofitInstance
import com.example.appmeli.model.DataMeli

import retrofit2.Response

class Repository {

    suspend fun getPost(search:String,name: String): Response<DataMeli> {
        return RetrofitInstance.api.getPost(search,name)
    }
}