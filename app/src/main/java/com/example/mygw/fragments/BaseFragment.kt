package com.example.mygw.fragments

import com.example.mygw.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment : DaggerFragment() {

    @set:Inject
    var viewModelProviderFactory: ViewModelProviderFactory? = null
}