package com.example.mygw.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygw.repository.MainRepository
import javax.inject.Inject

class ProjectViewModel @Inject constructor(
    var mainRepository: MainRepository,
    var context: Context
) : BaseViewModel(context as Application) {


    private val data: LiveData<String>
        get() = dataLiveData
    private val dataLiveData by lazy { MutableLiveData<String>() }

    fun loadProjects() {
        setData(mainRepository.loadProjects())
    }

    private fun setData(message: String) {
        dataLiveData.postValue(message)
    }

    fun getDataResponse(): LiveData<String> {
        return data
    }
}