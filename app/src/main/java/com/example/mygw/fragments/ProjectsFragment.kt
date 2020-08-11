package com.example.mygw.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mygw.R
import com.example.mygw.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_project.*


class ProjectsFragment : BaseFragment() {

    private val projectViewModel: ProjectViewModel by viewModels { viewModelProviderFactory!! }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscriptions()
        projectViewModel.loadProjects()

    }

    private fun subscriptions() {
        subscribeToLoadProjects()
    }

    private fun subscribeToLoadProjects() {
        val projects = projectViewModel.getDataResponse()
        projects.observe(viewLifecycleOwner, Observer { data -> textView3.text = data  })
    }
}