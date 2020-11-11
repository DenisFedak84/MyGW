package com.example.mygw.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotesModel(
    @field:PrimaryKey
    val question_id: Int,
    var userName: String,
    val link: String
)