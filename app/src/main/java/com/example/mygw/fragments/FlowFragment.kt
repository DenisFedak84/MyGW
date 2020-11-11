package com.example.mygw.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.mygw.R
import com.example.mygw.utils.Utils
import com.example.mygw.viewmodel.FlowViewModel
import kotlinx.android.synthetic.main.flow_item.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


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

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flowSubscriptions()

        saveButton.setOnClickListener(View.OnClickListener {
            flowViewModel.writeData(saveNameView.text.toString())
        })

        loadButton.setOnClickListener(View.OnClickListener {
            flowViewModel.readData()
        })

        loadNetworkButton.setOnClickListener(View.OnClickListener {
            flowViewModel.loadRestData()
        })

        saveDBButton.setOnClickListener(View.OnClickListener {
            flowViewModel.saveDB(Utils.getMockData())
        })

        loadDBButton.setOnClickListener(View.OnClickListener {
            flowViewModel.loadDB()
        })

        with(networkResultView, {
            text = "Result"
        })

        networkResultView.apply {
            text = "Result"
        }

        networkResultView.also {
            it.text = "Result"
        }

        var cat: String? = null
        cat?.let{
            println(it)} // не выводится
        cat = "Barsik"
        cat?.let{
            println(it)} // выводится

        fun testCat() {
            var mood = "I am sad"
            run {
                val mood = "I am happy"
                println(mood) // I am happy
            }
            println(mood)  // I am sad
        }
    }

    @InternalCoroutinesApi
    private fun flowSubscriptions() {
        subscribeToLoadSF()
        subscribeToSaveSF()
        subscribeToRestException()
        subscribeToLoadingSpinner()
        subscribeToRestData()
        subscribeToSaveDBData()
        subscribeToLoadDB()
        subscribeStateFlow()
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    private fun subscribeStateFlow() {
        lifecycleScope.launch {
            flowViewModel.countState.collect{value ->
                stateFlowResult.text = value.toString()
            }
        }
    }

    private fun subscribeToSaveDBData() {
        val result = flowViewModel.getSaveDBResultData()
        result.observe(viewLifecycleOwner, Observer {
            saveDBResultView.text = it.toString()
        })
    }

    private fun subscribeToLoadDB() {
        val result = flowViewModel.getloadDBResult()
        result.observe(viewLifecycleOwner, Observer {
            loadDBResultView.text = "values have been loaded :$it"
        })
    }

    private fun subscribeToRestData() {
        val result = flowViewModel.getRestData()
        result.observe(viewLifecycleOwner, Observer {
            networkResultView.text = it.size.toString()
        })
    }

    private fun subscribeToLoadingSpinner() {
        val result = flowViewModel.getSpinnerData()
        result.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
                networkResultView.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                networkResultView.visibility = View.VISIBLE
            }
        })
    }

    private fun subscribeToRestException() {
        val result = flowViewModel.getExceptionData()
        result.observe(viewLifecycleOwner, Observer {
            networkResultView.text = "Error"
        })
    }

    private fun subscribeToSaveSF() {
        val result = flowViewModel.getSaveData()
        result.observe(viewLifecycleOwner, Observer { data ->
            resultView.text = resources.getString(R.string.save_success, saveNameView.text)
        })
    }

    private fun subscribeToLoadSF() {
        val result = flowViewModel.getLoadData()
        result.observe(viewLifecycleOwner, Observer { data ->
            loadNameView.setText(data)
            resultView.text = resources.getString(R.string.load_success, data)
        })
    }


}