package com.example.mygw.dagger.module


import androidx.lifecycle.ViewModel
import com.example.mygw.viewmodel.FlowViewModel
import com.example.mygw.viewmodel.PagingViewModel
import com.example.mygw.viewmodel.PaintViewModel
import com.example.mygw.viewmodel.ProjectViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProjectViewModel::class)
    abstract fun bindProjectViewModel(projectViewModel : ProjectViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(PaintViewModel::class)
    abstract fun bindPaintViewModel(paintViewModel : PaintViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FlowViewModel::class)
    abstract fun bindFlowViewModel(flowViewModel : FlowViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PagingViewModel::class)
    abstract fun bindPagingViewModel(pagingViewModel : PagingViewModel): ViewModel
}