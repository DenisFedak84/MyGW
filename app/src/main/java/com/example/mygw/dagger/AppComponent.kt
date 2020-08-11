package com.example.mygw.dagger

import android.content.Context
import com.example.mygw.MyGWApplication
import com.example.mygw.dagger.module.ActivityModule
import com.example.mygw.dagger.module.FragmentModule
import com.example.mygw.dagger.module.NetworkModule
import com.example.mygw.dagger.module.ViewModelModule

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [ActivityModule::class, FragmentModule::class, NetworkModule::class, ViewModelModule::class, AndroidSupportInjectionModule::class])
interface AppComponent {

    fun inject(application: MyGWApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}