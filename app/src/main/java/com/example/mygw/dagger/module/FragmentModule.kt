package com.example.mygw.dagger.module


import com.example.mygw.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributePaintsFragmentInjector() : PaintsFragment

    @ContributesAndroidInjector
    abstract fun contributeProjectsFragmentInjector() : ProjectsFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailProjectsFragmentInjector() : DetailProjectFragment

    @ContributesAndroidInjector
    abstract fun contributeFlowFragmentInjector() : FlowFragment

    @ContributesAndroidInjector
    abstract fun contributePagingFragmentInjector() : PagingFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationFragmentInjector() : NotificationFragment
}