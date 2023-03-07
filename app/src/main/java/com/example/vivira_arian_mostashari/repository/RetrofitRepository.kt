package com.example.vivira_arian_mostashari.repository

import com.example.vivira_arian_mostashari.api.ApiService
import com.example.vivira_arian_mostashari.utils.PAGE_SIZE
import com.example.vivira_arian_mostashari.utils.TOKEN
import kotlinx.coroutines.CompletableJob
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitRepository @Inject constructor(
    private val apiService: ApiService,
) {
    var job: CompletableJob? = null

    suspend fun getRepositories(query: String, pageNumber: Int) =
        apiService.searchRepositories(query, pageNumber, PAGE_SIZE, "Bearer $TOKEN")

    fun cancleJobs() {
        job?.cancel()
    }
}