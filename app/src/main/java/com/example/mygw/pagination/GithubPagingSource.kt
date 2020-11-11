package com.example.mygw.pagination

import androidx.paging.PagingSource
import com.example.mygw.model.response.ItemStackOverflow
import com.example.mygw.network.NotesApi
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

class GithubPagingSource (private val notesApi: NotesApi) : PagingSource<Int, ItemStackOverflow>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemStackOverflow> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX

        return try {
            val response = notesApi.getNotes(position, params.loadSize)
            val repos = response.items
            LoadResult.Page(
                data = repos,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}