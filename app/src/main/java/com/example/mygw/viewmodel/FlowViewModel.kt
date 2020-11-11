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
import com.example.mygw.model.NotesModel
import com.example.mygw.model.response.ItemStackOverflow
import com.example.mygw.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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

    private val write: LiveData<String>
        get() = writeLiveData
    private val writeLiveData by lazy { MutableLiveData<String>() }

    private val read: LiveData<String>
        get() = readLiveData
    private val readLiveData by lazy { MutableLiveData<String>() }

    private val spinner: LiveData<Boolean>
        get() = spinnerLiveData
    private val spinnerLiveData by lazy { MutableLiveData<Boolean>() }

    private val exception: LiveData<String>
        get() = exceptionLiveData
    private val exceptionLiveData by lazy { MutableLiveData<String>() }

     @ExperimentalCoroutinesApi
     private val mutableCountState = MutableStateFlow(0)
     @ExperimentalCoroutinesApi
     val countState: StateFlow<Int> = mutableCountState


    val rest: LiveData<List<ItemStackOverflow>>
        get() = restLiveData
    private val restLiveData = MutableLiveData<List<ItemStackOverflow>>()

    val saveDBResult: LiveData<List<Long>>
        get() = saveDBResultLiveData
    private val saveDBResultLiveData = MutableLiveData<List<Long>>()

    val loadDBResult: LiveData<List<NotesModel>>
        get() = loadDBResultLiveData
    private val loadDBResultLiveData = MutableLiveData<List<NotesModel>>()


    @ExperimentalCoroutinesApi
    fun loadRestData() {
        setSpinnerData(true)
        viewModelScope.launch {
            mainRepository.getDataFromServer(1, 10)
                .catch { exception -> setExceptionData(exception.message!!) }
                .collect {
                    setSpinnerData(false)
                    restLiveData.value = it
                    mutableCountState.value = it.size
                }
        }
    }

    fun saveDB(mockData: List<NotesModel>) {
        viewModelScope.launch {
            mainRepository.saveDataInDB(mockData)
                .catch { exception -> setExceptionData(exception.message!!) }
                .collect {
                    val array = mutableListOf<Long>()
                    array.add(it)
                    saveDBResultLiveData.value = array
                }
        }
    }

    fun loadDB() {
        viewModelScope.launch {
            mainRepository.getDataFromDB()
                .catch { exception -> setExceptionData(exception.message!!) }
                .collect{
                    loadDBResultLiveData.value = it
                }
        }
    }

//     val rest: LiveData<List<ItemStackOverflow>> = liveData {
//        mainRepository.getDataFromServer(1, 10)
//            .onStart { setSpinnerData(true) }
//            .catch { exception -> setExceptionData(exception.message!!) }
//            .onCompletion { setSpinnerData(false) }
//            .collect{
//                emit(it)
//            }
//    }


    fun writeData(text: String) {
        viewModelScope.launch {
            dataStore.edit { data ->
                data[PreferencesKeys.USERNAME] = text
                setSaveData("Success")
            }
        }
    }

    fun readData() {
        viewModelScope.launch {
            dataStore.data
                .map { preferences ->
                    preferences[PreferencesKeys.USERNAME] ?: ""
                }.collect {
                    setLoadData(it)
                }
        }
    }


    fun baseFlowCall() {
        viewModelScope.launch {
            mainRepository.makeFlow().collect { value ->
                println("got $value")
            }
            println("flow is completed")
        }
    }

    private fun setLoadData(message: String) {
        readLiveData.postValue(message)
    }

    fun getLoadData(): LiveData<String> {
        return read
    }

    fun setSaveData(result: String) {
        writeLiveData.postValue(result)
    }

    fun getSaveData(): LiveData<String> {
        return write
    }

    fun setSpinnerData(result: Boolean) {
        spinnerLiveData.postValue(result)
    }

    fun getSpinnerData(): LiveData<Boolean> {
        return spinner
    }

    fun setExceptionData(result: String) {
        exceptionLiveData.postValue(result)
    }

    fun getExceptionData(): LiveData<String> {
        return exception
    }

    fun getRestData(): LiveData<List<ItemStackOverflow>> {
        return rest
    }

    fun getSaveDBResultData(): LiveData<List<Long>> {
        return saveDBResult
    }

    fun getloadDBResult() : LiveData<List<NotesModel>>{
        return loadDBResult
    }

}