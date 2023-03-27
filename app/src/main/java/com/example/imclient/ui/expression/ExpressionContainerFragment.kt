package com.example.imclient.ui.expression

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.imclient.R
import com.example.imclient.base.OnRecyclerViewItemClickListener
import com.example.imclient.databinding.FragmentExpressionContinerBinding
import com.example.imclient.ui.expression.adapter.ExpressTabAdapter
import com.example.imclient.ui.expression.entities.ExpressTabEntity
import com.example.imclient.ui.main.helper.InputBoxViewHolder
import com.example.imclient.utils.Logger

class ExpressionContainerFragment : Fragment(), OnRecyclerViewItemClickListener<ExpressTabEntity> {

    var binding: FragmentExpressionContinerBinding? = null
    var tabAdapter = ExpressTabAdapter().apply {
        registerOnItemClickListener(this@ExpressionContainerFragment)
    }
    private val tabList = arrayListOf<ExpressTabEntity>(
        ExpressTabEntity(
            R.drawable.icon_expression
        ),
        ExpressTabEntity(
            R.drawable.icon_voice
        ),
        ExpressTabEntity(
            R.drawable.icon_add
        )
    )

    companion object {
        var inputBoxViewHolder: InputBoxViewHolder? = null
        fun newInstance(inputBoxViewHolder: InputBoxViewHolder): ExpressionContainerFragment {
            this.inputBoxViewHolder = inputBoxViewHolder
            return ExpressionContainerFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpressionContinerBinding.inflate(inflater, container, false)
        initView()
        return binding?.root
    }

    // 创建 ViewPager2 的页面变更回调接口实例
    val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        @SuppressLint("NotifyDataSetChanged")
        override fun onPageSelected(position: Int) {
            Logger.log("onItemClick = 111 position = $position")
            // 当 ViewPager2 滑动到新的页面时，更新 RecyclerView 中的选中项
            tabAdapter.selectedPosition = position
            tabAdapter.notifyDataSetChanged()
        }
    }



    private fun initView() {
        binding?.run {
            expressTab.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            var expressTabAdapter = tabAdapter
            expressTab.adapter = expressTabAdapter
            expressTabAdapter.addAll(tabList)
            val fragmentList = arrayListOf<Fragment>()
            fragmentList.add(ExpressionFragment.newInstance(inputBoxViewHolder!!))
            fragmentList.add(ExpressionFragment.newInstance(inputBoxViewHolder!!))
            fragmentList.add(MapBigExpressionFragment.newInstance())
            expressionRv.adapter = object : FragmentStateAdapter(requireActivity()) {
                override fun getItemCount(): Int {
                    return fragmentList.size
                }

                override fun createFragment(position: Int): Fragment {
                    return fragmentList[position]
                }
            }
            // 注册页面变更回调接口
            expressionRv.registerOnPageChangeCallback(onPageChangeCallback)
        }
    }

    override fun onItemClick(v: View, t: ExpressTabEntity, position: Int) {
        Logger.log("onItemClick = position = $position")
        binding?.expressionRv?.currentItem = position
    }

}