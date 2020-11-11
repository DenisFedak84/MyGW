package com.example.mygw.model.response

data class StackOverflowModel(
    val items: List<ItemStackOverflow>,
    val has_more: Boolean,
    val quota_max: Int,
    val quota_remaining: Int
)