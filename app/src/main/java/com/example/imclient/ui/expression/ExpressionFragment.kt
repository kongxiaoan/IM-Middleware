package com.example.imclient.ui.expression

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imclient.base.OnRecyclerViewItemClickListener
import com.example.imclient.databinding.FragmentExpressionBinding
import com.example.imclient.ui.expression.adapter.EmojiAdapter
import com.example.imclient.ui.expression.entities.EmojiEntry
import com.example.imclient.ui.main.helper.InputBoxViewHolder
import com.example.imclient.utils.Logger
import com.example.imclient.utils.getCompatEmojiString
import com.example.imclient.utils.observe
import com.example.imclient.utils.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class ExpressionFragment : Fragment() {

    private var binding: FragmentExpressionBinding? = null

    private val mAdapter: EmojiAdapter = EmojiAdapter().apply {
        registerOnItemClickListener(object : OnRecyclerViewItemClickListener<EmojiEntry> {
            override fun onItemClick(v: View, t: EmojiEntry, position: Int) {
                inputBoxViewHolder.editText(t.emoji.getCompatEmojiString().toString())
            }
        })
    }
    private val viewModel by activityViewModels<ExpressionViewModel>()

    companion object {
        lateinit var inputBoxViewHolder: InputBoxViewHolder
        fun newInstance(inputBoxViewHolder: InputBoxViewHolder): ExpressionFragment {
            Companion.inputBoxViewHolder = inputBoxViewHolder
            return ExpressionFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpressionBinding.inflate(inflater, container, false)
        initView()
        loadData()
        return binding?.root
    }

    private fun loadData() {
        viewModel.run {
            viewModel(this) {
                MainScope().launch(Dispatchers.IO) {
                    Logger.log("当前线程 ${Thread.currentThread().name}")
                    observe(getEmojiList(), ::renderEmoji)
                }
            }
        }
    }

    private fun renderEmoji(emojiEntries: List<EmojiEntry>?) {
        if (!emojiEntries.isNullOrEmpty()) {
            MainScope().launch(Dispatchers.Main) {
                mAdapter.addAll(emojiEntries)
            }
        }
    }

    private fun initView() {
        binding?.run {
            expressionRv.layoutManager =
                GridLayoutManager(this@ExpressionFragment.requireContext(), 6)
            expressionRv.adapter = mAdapter
        }
    }

}