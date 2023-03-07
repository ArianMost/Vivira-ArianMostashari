package com.example.vivira_arian_mostashari.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.vivira_arian_mostashari.paging.RepositoriesPagingSource
import com.example.vivira_arian_mostashari.repository.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(private val retrofitRepository: RetrofitRepository) :
    ViewModel() {

    private val _query: MutableLiveData<String> = MutableLiveData()

    fun getQuery(): String? {
        return _query.value
    }

    fun setQuery(query: String) {
        val newValue = query
        if (_query.value.equals(query) || query.isEmpty()) {
            return
        }
        _query.value = newValue
    }

    fun cancelJobs() {
        retrofitRepository.cancleJobs()
    }

    val listData = Pager(PagingConfig(pageSize = 1)) {
        RepositoriesPagingSource(retrofitRepository, _query.value.toString())
    }
}