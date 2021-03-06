package com.example.challenge5_afifuddin.service

import com.example.challenge5_afifuddin.model_movies_now_showing.GetAllMovieNowShowing
import com.example.challenge5_afifuddin.model_movies_now_showing.Result
import com.example.challenge5_afifuddin.model_movies_top_rated.GetTopRated
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getAllNowShowing(): GetAllMovieNowShowing
    @GET("movie/top_rated")
    suspend fun getTopRated():GetTopRated
}