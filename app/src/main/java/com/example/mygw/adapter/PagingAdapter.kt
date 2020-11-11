package com.example.mygw.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mygw.model.response.ItemStackOverflow

class PagingAdapter : PagingDataAdapter<ItemStackOverflow, RecyclerView.ViewHolder>(REPO_COMPARATOR)  {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val stackItem = getItem(position)
        if (stackItem != null) {
            (holder as PagingViewHolder).bind(stackItem, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PagingViewHolder.create(parent)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ItemStackOverflow>() {
            override fun areItemsTheSame(oldItem: ItemStackOverflow, newItem: ItemStackOverflow): Boolean =
                oldItem.question_id == newItem.question_id

            override fun areContentsTheSame(oldItem: ItemStackOverflow, newItem: ItemStackOverflow): Boolean =
                oldItem == newItem
        }
    }
}