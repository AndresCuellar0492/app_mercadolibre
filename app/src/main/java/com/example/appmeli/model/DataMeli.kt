package com.example.appmeli.model

data class DataMeli(val results: List<DataResult>)


data class DataResult(
    val title: String,
    val price: String,
    val thumbnail: String,
    val available_quantity: String,
    val permalink:String
)

