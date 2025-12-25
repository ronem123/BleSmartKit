package com.ram.mandal.blesmartkit.data.network


import com.ram.mandal.blesmartkit.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("androidapp/get-article-json.php")
    suspend fun getNews(
        @Query("type") country: String = "1",
        @Query("fbclid") pageNum: String = "IwAR3ZEbtRGyHewHmtWX4pKXii7cn04A2DlQ8RAc5NQutk1hjTRcXKtVyHMGQ",
    ): NewsResponse


    @GET("androidapp/get-article-json.php")
    suspend fun getNewsDetail(
        @Query("type") country: String = "1",
        @Query("fbclid") pageNum: String = "IwAR3ZEbtRGyHewHmtWX4pKXii7cn04A2DlQ8RAc5NQutk1hjTRcXKtVyHMGQ",
    ): NewsResponse


}