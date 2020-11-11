package com.example.mygw.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mygw.model.NotesModel

@Database(entities = [NotesModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}