package com.example.vivira_arian_mostashari

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vivira_arian_mostashari.adapter.LoadMoreAdapter
import com.example.vivira_arian_mostashari.adapter.RepositoriesAdapter
import com.example.vivira_arian_mostashari.databinding.ActivityMainBinding
import com.example.vivira_arian_mostashari.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repositoriesAdapter: RepositoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        fun View.hideKeyboard() {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }

        binding.apply {
            btnSearch.setOnClickListener {
                if (edtSearch.text.toString().isNotEmpty()) {
                    // Hide keyboard after tapping search button
                    edtSearch.hideKeyboard()
                    searchFunction()
                }
            }
            edtSearch.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Hide keyboard after tapping search button
                    edtSearch.hideKeyboard()
                    searchFunction()
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun searchFunction() {
        // Reset the previous list before showing a new one
        resetPreviousList()

        // Set the query value
        viewModel.setQuery(binding.edtSearch.text.toString())

        // Get data from API and set them in the adapter
        getDataAndSetAdapter()

        // Represent the list with the fetched data
        setRecyclerView()

        // Handle progress bar
        handleProgressBar()

        // Load more data at the end of the list
        loadMoreData()
    }

    private fun resetPreviousList() {
        binding.listRepo.itemAnimator?.let { binding.listRepo.itemAnimator = null }
        binding.listRepo.adapter = null
    }

    private fun getDataAndSetAdapter() {
        lifecycleScope.launch {
            viewModel.listData.flow.collectLatest {
                repositoriesAdapter.submitData(it)
            }
        }
    }

    private fun setRecyclerView() {
        binding.listRepo.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = repositoriesAdapter
        }
    }

    private fun handleProgressBar() {
        lifecycleScope.launchWhenCreated {
            repositoriesAdapter.loadStateFlow.collect{
                val state = it.refresh
                binding.progressBar.isVisible = state is LoadState.Loading
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        binding.edtSearch.setText(viewModel.getQuery())
        getDataAndSetAdapter()
        setRecyclerView()
        loadMoreData()
    }

    private fun loadMoreData() {
        binding.listRepo.adapter = repositoriesAdapter.withLoadStateFooter(
            LoadMoreAdapter{
                repositoriesAdapter.retry()
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }
}