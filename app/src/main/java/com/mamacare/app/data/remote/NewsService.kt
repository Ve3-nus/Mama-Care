package com.mamacare.app.data.remote

import com.mamacare.app.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/everything")
    suspend fun getMaternalHealthNews(
        @Query("q") query: String = "pregnancy OR maternal health AND Kenya",
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): NewsResponse
}