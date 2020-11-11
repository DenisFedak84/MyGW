package com.example.mygw.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.mygw.R

class PagingLoadStateViewHolder(view: View, retry: () -> Unit) : RecyclerView.ViewHolder(view) {

    private val retryButton: Button = view.findViewById(R.id.retry_button)
    private val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
    private val errorMsg: TextView = view.findViewById(R.id.error_msg)

    init {
        retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorMsg.text = loadState.error.localizedMessage
        }
        progressBar.isVisible = loadState is LoadState.Loading
        retryButton.isVisible = loadState !is LoadState.Loading
        errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): PagingLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.footer_paging_item, parent, false)
            return PagingLoadStateViewHolder(view, retry)
        }
    }

}