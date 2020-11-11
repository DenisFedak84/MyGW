package com.example.mygw.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mygw.db.NotesDao
import com.example.mygw.model.FolderModel
import com.example.mygw.model.FooterModel
import com.example.mygw.model.NotesModel
import com.example.mygw.model.ProjectModel
import com.example.mygw.model.response.ItemStackOverflow
import com.example.mygw.network.NotesApi
import com.example.mygw.pagination.GithubPagingSource
import com.example.mygw.utils.Utils
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MainRepository @Inject constructor(private val notesApi: NotesApi, private val notesDao: NotesDao) {

    private val storageRef = Firebase.storage.reference
    private val urls = mutableListOf<Any>()

    fun loadPaints(): String {
        return "Projects have been loaded"
    }

    suspend fun loadDataFromFirestore(
        path: String,
        footer: String,
        folder: Boolean
    ): MutableList<Any> {
        urls.clear()
        addFooter(footer)
        return try {
            getDataFromFirestore(path, folder)
        } catch (e: Exception) {
            Log.d(TAG, e.message)
            emptyArray<Any>().toMutableList()
        }
    }

    private fun addFooter(footer: String) {
        urls.add(FooterModel(name = footer))
    }

    private suspend fun getDataFromFirestore(
        path: String,
        folder: Boolean
    ): MutableList<Any> {
        return try {
            val items = storageRef.child(path).listAll().await()
            return if (folder) {
                downloadFolders(items)
            } else {
                downloadUrls(items)
            }
        } catch (e: Exception) {
            emptyArray<Any>().toMutableList()
        }
    }

    private fun downloadFolders(items: ListResult): MutableList<Any> {
        val prefix = items.prefixes
        for (i in prefix) {
            val name = i.toString().substring(i.toString().lastIndexOf("/") + 1)
            val item = FolderModel(name)
            urls.add(item)
        }
        return urls
    }

    private suspend fun downloadUrls(
        images: ListResult?
    ): MutableList<Any> {

        for (i in images!!.items) {
            val url = i.downloadUrl.await()
            val item = ProjectModel(url.toString())
            urls.add(item)
        }
        return urls
    }

    fun deleteItem(path: String): Boolean {
        try {
            val result = storageRef.child(path).delete()
        } catch (exeptions: Exception) {
            println()
        }
        return false
    }

    suspend fun getDataFromServer(page: Int, size: Int): Flow<List<ItemStackOverflow>> {
        return listOf(notesApi.getNotes(page, size))
            .map { dataFromApi -> Utils.convertItems(dataFromApi) }
            .asFlow()
            .flowOn(Dispatchers.IO)
    }

    fun makeFlow() = flow {
        println("sending first value")
        emit(1)
        println("first value collected, sending another value")
        emit(2)
        println("second value collected, sending a third value")
        emit(3)
        println("done")
    }

   suspend fun saveDataInDB(mockData: List<NotesModel>): Flow<Long> {
       return notesDao.insertAll(mockData).asFlow().flowOn(Dispatchers.IO)
//       return notesDao.insert(mockData).map {
//           it[0]
//       }
    }

    suspend fun getDataFromDB() : Flow<List<NotesModel>>{
        return notesDao.getAll()
    }

    fun getSearchResultStream(): Flow<PagingData<ItemStackOverflow>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false ),
            pagingSourceFactory = { GithubPagingSource(notesApi) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20

    }

}