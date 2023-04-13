package com.example.imclient.ui.box_more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imclient.R
import com.example.imclient.databinding.FragmentInputBoxMoreBinding
import com.example.imclient.ui.expression.adapter.MapBigExpressionAdapter
import com.example.imclient.ui.expression.entities.MapBigExpressionEntity

class InputBoxMoreFragment : Fragment() {

    companion object {
        fun newInstance() = InputBoxMoreFragment()
    }

    private var binding: FragmentInputBoxMoreBinding? = null

    private val viewModel = activityViewModels<InputBoxMoreViewModel>()

    private val mAdapter: MapBigExpressionAdapter = MapBigExpressionAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentInputBoxMoreBinding.inflate(inflater, container, false)
        initView()
        loadData()
        return binding?.root
    }

    private fun loadData() {
        var listOf = arrayListOf<MapBigExpressionEntity>()
        for (i in 0..6) {
            listOf.add(MapBigExpressionEntity(requireContext().getString(R.string.im_picture)))
        }

        mAdapter.addAll(listOf)
    }

    private fun initView() {
        binding?.run {
            imMiddlewareMoreRV.layoutManager =
                GridLayoutManager(this@InputBoxMoreFragment.requireContext(), 4)
            imMiddlewareMoreRV.adapter = mAdapter
        }
    }
}
