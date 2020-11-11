package com.example.mygw.model.response

data class OwnerStackOverflow(
    val reputation: String,
    val user_id: Int,
    val user_type: String,
    val profile_image: String,
    val display_name: String,
    val link: String
)