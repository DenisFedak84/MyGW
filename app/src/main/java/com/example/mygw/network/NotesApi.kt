package com.example.mygw.network

import com.example.mygw.model.response.StackOverflowModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The interface which provides methods to get result of webservices
 */
interface NotesApi {
    /**
     * Get the list of the pots from the API
     */

    @GET("/2.2/answers?order=desc&sort=activity&site=stackoverflow")
    suspend fun getNotes(
        @Query("page") pageNumber: Int,
        @Query("pagesize") pageSize: Int
    ): StackOverflowModel
}