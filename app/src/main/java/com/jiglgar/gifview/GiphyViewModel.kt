package com.jiglgar.gifview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GiphyViewModel : ViewModel() {
    private val api = Retrofit.Builder()
        .baseUrl("https://api.giphy.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GiphyApi::class.java)

    private val _gifList = MutableLiveData<List<GifItem>>()
    val gifList: LiveData<List<GifItem>> = _gifList

    fun getTrendingGifs(apiKey: String, limit: Int) {
        viewModelScope.launch {
            try {
                val response = api.getTrendingGifs(apiKey, limit)
                _gifList.value = response.data
            } catch (e: Exception) {
                Log.e("GiphyViewModel", "Error getting trending GIFs", e)
            }
        }
    }
}




