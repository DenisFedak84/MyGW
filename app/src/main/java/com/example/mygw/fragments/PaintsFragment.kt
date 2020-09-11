package com.example.mygw.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mygw.R
import com.example.mygw.viewmodel.PaintViewModel
import kotlinx.android.synthetic.main.fragment_paint.*

class PaintsFragment : BaseFragment() {

    private val paintViewModel: PaintViewModel by viewModels { viewModelProviderFactory!! }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_paint, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriptionsPaints()
        paintViewModel.loadPaints()
    }

      fun subscriptionsPaints() {
        subscribeToLoadPaints()
    }

    private fun subscribeToLoadPaints() {
        val paints = paintViewModel.getDataResponse()
        paints.observe(viewLifecycleOwner, Observer { data -> textView2.text = data  })
    }
}