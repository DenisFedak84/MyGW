package com.example.mygw.db

import androidx.room.*
import com.example.mygw.model.NotesModel
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {

    @Query("SELECT * FROM notesModel")
    fun getAll(): Flow<List<NotesModel>>

//    @Query("SELECT * from plants ORDER BY name")
//    fun getPlantsFlow(): Flow<List<Plant>>

    @Query("SELECT * FROM notesModel WHERE question_id = :id")
    fun getById(id: Long): NotesModel

    @Delete
    suspend fun delete(notesModel: NotesModel): Int

    @Query("DELETE FROM notesModel")
    suspend fun deleteAll(): Int

//    @Insert(onConflict = REPLACE)
//    fun insert( notesModel: List<NotesModel>): Flow<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notesModel: List<NotesModel>) : List<Long>

    @Update
    fun update(vararg notesModel: NotesModel)

}