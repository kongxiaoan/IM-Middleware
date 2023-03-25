package com.example.imclient.ui.expression

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.im.middleware.R

class MapBigExpressionFragment : Fragment() {

    companion object {
        fun newInstance() = MapBigExpressionFragment()
    }

    private lateinit var viewModel: MapBigExpressionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_big_expression, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapBigExpressionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}