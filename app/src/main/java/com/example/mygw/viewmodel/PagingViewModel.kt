package com.example.mygw.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mygw.model.response.ItemStackOverflow
import com.example.mygw.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagingViewModel @Inject constructor(
    var mainRepository: MainRepository,
    var context: Context
) : BaseViewModel(context as Application) {

    fun loadPagingData(): Flow<PagingData<ItemStackOverflow>> {
        return mainRepository.getSearchResultStream().cachedIn(viewModelScope)
    }
}