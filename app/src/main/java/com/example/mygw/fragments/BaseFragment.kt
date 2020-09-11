package com.example.mygw.fragments

import android.graphics.Canvas
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygw.R
import com.example.mygw.ViewModelProviderFactory
import com.example.mygw.adapter.CallbackItemClick
import com.example.mygw.adapter.ProjectAdapter
import com.example.mygw.model.FolderModel
import com.example.mygw.model.ProjectModel
import com.example.mygw.viewmodel.ProjectViewModel
import dagger.android.support.DaggerFragment
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.fragment_project.*
import javax.inject.Inject

open class BaseFragment : DaggerFragment(), CallbackItemClick {

    lateinit var adapter: ProjectAdapter
    val projectViewModel: ProjectViewModel by viewModels { viewModelProviderFactory!! }

    @set:Inject
    var viewModelProviderFactory: ViewModelProviderFactory? = null

    fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        adapter = activity?.let { ProjectAdapter(this, it) }!!
        recyclerView.adapter = adapter
        addItemTouchHelper()
    }

    fun addItemTouchHelper() {

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    var position = viewHolder.layoutPosition
                    var item = adapter.adapterDataList[position]
                    if (item is ProjectModel) {
                        var path = item.url
                        deleteItem(path)

                    } else if (item is FolderModel) {
                        var path = item.name
                        deleteItem(path)
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    RecyclerViewSwipeDecorator.Builder(
                        activity,
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addBackgroundColor(
                            ContextCompat.getColor(
                                activity!!,
                                R.color.colorRed
                            )
                        )
                        .addActionIcon(R.drawable.ic_baseline_delete_sweep_24)
                        .create()
                        .decorate()
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }

                override fun getSwipeDirs(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    if (viewHolder is ProjectAdapter.FooterViewHolder) return 0
                    return super.getSwipeDirs(recyclerView, viewHolder)
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun deleteItem(path: String) {
        projectViewModel.delete(path)
    }

    override fun itemClick(path: String) {
        TODO("Not yet implemented")
    }

    fun subscriptions() {
        subscribeToLoadProjects()
        subscribeToDeleteItem()
    }

    private fun subscribeToDeleteItem() {
        val delete = projectViewModel.getDeleteResponse()
        delete.observe(viewLifecycleOwner, Observer { data ->
            if (data) {
                Toast.makeText(activity, "Item was deleted", Toast.LENGTH_LONG).show()
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun subscribeToLoadProjects() {
        val projects = projectViewModel.getDataResponse()
        projects.observe(viewLifecycleOwner, Observer { data ->
            adapter.updateItems(data)
        })
    }
}