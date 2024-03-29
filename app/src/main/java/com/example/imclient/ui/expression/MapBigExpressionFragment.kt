package com.example.imclient.ui.expression

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.imclient.R

class MapBigExpressionFragment : Fragment() {

    companion object {
        fun newInstance() = MapBigExpressionFragment()
    }

    private lateinit var viewModel: MapBigExpressionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_map_big_expression, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapBigExpressionViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
