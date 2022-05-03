package com.example.appmeli

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmeli.model.DataMeli
import com.example.appmeli.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {
    val myResponse: MutableLiveData<Response<DataMeli>> = MutableLiveData()

    fun getPost(search:String,name: String) {
        viewModelScope.launch {
            val response: Response<DataMeli> = repository.getPost(search,name)
            myResponse.value = response
        }
    }
}