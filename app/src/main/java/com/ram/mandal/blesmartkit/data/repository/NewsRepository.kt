package com.ram.mandal.blesmartkit.data.repository

import com.ram.mandal.blesmartkit.data.network.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NepalTrialRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getNews(country: String, pageNum: String) =
        flow { emit(apiService.getNews()) }

    suspend fun getNewsDetail(newsId: String) = flow { emit(apiService.getNewsDetail()) }

}