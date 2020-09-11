package com.example.mygw.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mygw.R
import com.example.mygw.viewmodel.FlowViewModel
import kotlinx.android.synthetic.main.fragment_flow.*

class FlowFragment : BaseFragment() {

    private val flowViewModel: FlowViewModel by viewModels { viewModelProviderFactory!! }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flowSubscriptions()

        saveButton.setOnClickListener(View.OnClickListener {
            flowViewModel.saveData(saveNameView.text.toString())
        })

        loadButton.setOnClickListener(View.OnClickListener {
            flowViewModel.loadData(loadNameView.text.toString())
        })


    }

    private fun flowSubscriptions() {
        subscribeToLoadSF()
        subscribeToSaveSF()
    }

    private fun subscribeToSaveSF() {
        val saveResult = flowViewModel.getSaveData()
        saveResult.observe(viewLifecycleOwner, Observer { data ->
            resultView.text = resources.getString(R.string.save_success, saveNameView.text)
        })
    }

    private fun subscribeToLoadSF() {
   val loadResult = flowViewModel.getLoadData()
        loadResult.observe(viewLifecycleOwner, Observer { data->
            resultView.text = resources.getString(R.string.load_success, data)
        })
    }


}