package com.example.vivira_arian_mostashari.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.vivira_arian_mostashari.models.Item
import com.example.vivira_arian_mostashari.repository.RetrofitRepository
import retrofit2.HttpException

class RepositoriesPagingSource(
    private val repository: RetrofitRepository, private val query: String
) : PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getRepositories(query, currentPage)
            val data = response.body()?.items
            val responseData = mutableListOf<Item>()
            data?.let { responseData.addAll(it) }

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }


    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return null
    }


}