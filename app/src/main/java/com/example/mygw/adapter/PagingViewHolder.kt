package com.example.mygw.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mygw.R
import com.example.mygw.model.response.ItemStackOverflow

class PagingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val id: TextView = view.findViewById(R.id.idView)
    private val name: TextView = view.findViewById(R.id.nameView)
    private var item : ItemStackOverflow? = null
    private var itemPosition : Int = 0

    init {
        view.setOnClickListener {
            println("Click on position = $itemPosition" )
        }
    }

        companion object {
            fun create(parent: ViewGroup): PagingViewHolder{
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.paging_item, parent, false)
                return PagingViewHolder(view)
            }
        }

    fun bind(item: ItemStackOverflow?, position: Int) {
        itemPosition = position
        this.item = item
        if (item!=null){
            id.text = item.owner.user_id.toString()
            name.text = item.owner.display_name
        }

    }
}