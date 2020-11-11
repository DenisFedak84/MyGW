package com.example.mygw.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mygw.repository.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProjectViewModel @Inject constructor(
    var mainRepository: MainRepository,
    var context: Context
) : BaseViewModel(context as Application) {

    private val data: LiveData<List<Any>>
        get() = dataLiveData
    private val dataLiveData by lazy { MutableLiveData<List<Any>>() }

    private val delete: LiveData<Boolean>
        get() = deleteLiveData
    private val deleteLiveData by lazy { MutableLiveData<Boolean>() }

    fun loadProjects(path: String, footer: String, folder: Boolean) {
        viewModelScope.launch {
            setData(mainRepository.loadDataFromFirestore(path, footer, folder))
        }
    }

    fun delete(path: String) {
        setDeleteResult(mainRepository.deleteItem(path))
    }

    private fun setData(message: List<Any>) {
        dataLiveData.postValue(message)
    }

    fun getDataResponse(): LiveData<List<Any>> {
        return data
    }

    fun getDeleteResponse(): LiveData<Boolean> {
        return delete
    }

    fun setDeleteResult(result: Boolean) {
        deleteLiveData.postValue(result)
    }
}