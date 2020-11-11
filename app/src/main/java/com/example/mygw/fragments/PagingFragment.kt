package com.example.mygw.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygw.R
import com.example.mygw.adapter.PagingAdapter
import com.example.mygw.adapter.PagingLoadStateAdapter
import com.example.mygw.viewmodel.PagingViewModel
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PagingFragment : BaseFragment() {

    private val pagingViewModel: PagingViewModel by viewModels { viewModelProviderFactory!! }
    private val pagingAdapter = PagingAdapter()
    private var dataJob: Job? = null

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_paging, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initAdapter()
        getData()

//        val paper = PaperBox(10)
//        val iron = IronBox(15,15)
//        Derived(paper).print()
//        Derived(iron).print()
//        Delivery(paper)
    }

    private fun getData() {
        dataJob?.cancel()
        dataJob = lifecycleScope.launch {
            pagingViewModel.loadPagingData().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = pagingAdapter

        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)
    }

    private fun initAdapter() {
        recyclerView.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = PagingLoadStateAdapter { pagingAdapter.retry() },
            footer = PagingLoadStateAdapter { pagingAdapter.retry() }
        )
//        lifecycleScope.launch {
//            pagingAdapter.loadStateFlow.collectLatest { loadStates ->
//
//            }
//        }
    }
}