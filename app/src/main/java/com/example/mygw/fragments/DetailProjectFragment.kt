package com.example.mygw.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygw.R
import com.example.mygw.adapter.CallbackItemClick
import com.example.mygw.adapter.ProjectAdapter
import com.example.mygw.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_project.*

class DetailProjectFragment : BaseFragment(), CallbackItemClick {

    private val projectViewModel: ProjectViewModel by viewModels { viewModelProviderFactory!! }
    lateinit var adapter: ProjectAdapter

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscriptions()
       arguments?.let {
          val arg = DetailProjectFragmentArgs.fromBundle(it)
           projectViewModel.loadProjects(arg.pathArg, arg.footerArg,false)
       }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        adapter = activity?.let { ProjectAdapter(this, it) }!!
        recyclerView.adapter = adapter
    }

    private fun subscriptions() {
        subscribeToLoadProjects()
    }

    private fun subscribeToLoadProjects() {
        val projects = projectViewModel.getDataResponse()
        projects.observe(viewLifecycleOwner, Observer { data ->
            adapter.updateItems(data)
        })
    }

    override fun itemClick(path: String) {
    }
}