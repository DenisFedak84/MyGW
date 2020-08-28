package com.example.mygw.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygw.R
import com.example.mygw.fragments.ProjectsFragmentDirections
import com.example.mygw.model.FolderModel
import com.example.mygw.model.FooterModel
import com.example.mygw.model.ProjectModel
import kotlinx.android.synthetic.main.adapter_item.view.*
import kotlinx.android.synthetic.main.footer_item.view.*


class ProjectAdapter(var callbackItemClick: CallbackItemClick,  val context: Context) : RecyclerView.Adapter<ProjectAdapter.BaseViewHolder<*>>() {

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    private var adapterDataList: List<Any> = emptyList()

    companion object {
        private const val TYPE_FOOTER = 0
        private const val TYPE_DATA = 1
        private const val TYPE_FOLDER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_FOOTER -> {
                val view = LayoutInflater.from(context).inflate(R.layout.footer_item, parent, false)
                FooterViewHolder(view)
            }
            TYPE_DATA -> {
                val view = LayoutInflater.from(context).inflate(R.layout.adapter_item, parent, false)
                ProjectViewHolder(view)
            }
            TYPE_FOLDER ->{
                val view = LayoutInflater.from(context).inflate(R.layout.footer_item, parent, false)
                FolderViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = adapterDataList[position]
        when (holder) {
            is FooterViewHolder -> holder.bind(element as FooterModel)
            is ProjectViewHolder -> holder.bind(element as ProjectModel)
            is FolderViewHolder -> holder.bind(element as FolderModel)
            else -> throw IllegalArgumentException()
        }
    }


    inner class FooterViewHolder(itemView: View) : BaseViewHolder<FooterModel>(itemView) {

        override fun bind(item: FooterModel) = with(itemView) {
            textView.text = item.name

        }
    }

    inner class FolderViewHolder(itemView: View) : BaseViewHolder<FolderModel>(itemView) {

        override fun bind(item: FolderModel) = with(itemView) {
            var title = item.name
            textView.text = title
            textView.setOnClickListener(View.OnClickListener {
//                callbackItemClick.itemClick(item.name)
                val action = ProjectsFragmentDirections.actionProjectFragmentToDetailProjectFragment(pathArg = "/Projects/$title/", footerArg = title)
                it.findNavController().navigate(action)
            })
        }
    }

    inner class ProjectViewHolder(itemView: View) : BaseViewHolder<ProjectModel>(itemView) {
        override fun bind(item: ProjectModel): Unit = with(itemView)  {
            Glide.with(context)
                .load(item.url)
                .into(image)
        }
    }

    override fun getItemCount(): Int {
        return adapterDataList.size
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = adapterDataList[position]
        return when (comparable) {
            is FooterModel -> TYPE_FOOTER
            is ProjectModel -> TYPE_DATA
            is FolderModel -> TYPE_FOLDER

            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    fun updateItems(data:List<Any>){
        adapterDataList = data
        notifyDataSetChanged()
    }
}

