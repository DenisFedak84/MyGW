package com.example.mygw.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.example.mygw.R
import com.example.mygw.adapter.CallbackItemClick

class DetailProjectFragment : BaseFragment(), CallbackItemClick {

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
            projectViewModel.loadProjects(arg.pathArg, arg.footerArg, false)
        }
    }

}