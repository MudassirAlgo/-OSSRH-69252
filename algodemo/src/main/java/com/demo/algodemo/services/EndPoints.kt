package com.demo.algodemo.services

import com.demo.algodemo.model.NewsResponse
import retrofit2.Call
import retrofit2.http.*

interface EndPoints {
    @GET("everything?q=tesla&from=2021-04-20&sortBy=publishedAt&apiKey=d00b8142f86046edbb6bfa5434607a43")
    fun getNews(): Call<NewsResponse>


}


