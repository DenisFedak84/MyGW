package com.example.mygw

import android.app.Activity
import android.app.Application
import com.example.mygw.dagger.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MyGWApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @set:Inject
    var dispatchingAndroidInjectorActivity : DispatchingAndroidInjector<Activity>? = null

    @set:Inject
    var dispatchingAndroidInjectorFragment : DispatchingAndroidInjector<androidx.fragment.app.Fragment>? = null

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
            .builder()
            .context(this)
            .build()
            .inject(this)
    }


    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjectorActivity
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<androidx.fragment.app.Fragment>? {
        return dispatchingAndroidInjectorFragment
    }


}