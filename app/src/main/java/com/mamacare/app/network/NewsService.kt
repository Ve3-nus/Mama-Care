package com.example.mamacare_api_test.network

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/everything")
    suspend fun getMaternalHealthNews(
        @Query("q") query: String = "pregnancy AND maternal health",
        @Query("apiKey") apiKey: String = "c01908192e9f4edb974ca7c356acb101"
    ): NewsResponse
}
