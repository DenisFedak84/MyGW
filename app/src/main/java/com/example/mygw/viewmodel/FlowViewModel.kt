package com.example.mygw.viewmodel

import android.app.Application
import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mygw.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class FlowViewModel @Inject constructor(
    var mainRepository: MainRepository,
    var context: Context
) : BaseViewModel(context as Application) {

    object PreferencesKeys {

        val USERNAME = preferencesKey<String>("username")

    }


    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "settings"
    )

    private val save: LiveData<String>
        get() = saveLiveData
    private val saveLiveData by lazy { MutableLiveData<String>() }

    private val load: LiveData<String>
        get() = loadLiveData
    private val loadLiveData by lazy { MutableLiveData<String>() }

    fun saveData(text: String) {
        viewModelScope.launch {
            dataStore.edit { data ->
                data[PreferencesKeys.USERNAME] = text
                setSaveData("Success")
            }
        }
    }

    fun loadData(text: String) {
        
        viewModelScope.launch {

            val exampleCounterFlow: Flow<String> = dataStore.data
                .map { preferences ->
                    preferences[PreferencesKeys.USERNAME] ?: ""
                }

            exampleCounterFlow.collect { data ->
                setLoadData(data) }

        }

    }

    private fun setLoadData(message: String) {
        loadLiveData.postValue(message)
    }

    fun getLoadData(): LiveData<String> {
        return load
    }

    fun setSaveData(result: String) {
        saveLiveData.postValue(result)
    }

    fun getSaveData(): LiveData<String> {
        return save
    }
}