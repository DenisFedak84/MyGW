package com.example.mygw.dagger.module


import com.example.mygw.fragments.DetailProjectFragment
import com.example.mygw.fragments.PaintsFragment
import com.example.mygw.fragments.ProjectsFragment
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

}