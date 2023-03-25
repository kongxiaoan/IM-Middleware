package com.example.imclient.ui.main.adapter.holder.strategy

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.imclient.base.OnRecyclerViewItemClickListener

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description: 聊天策略
 */
abstract class IMMainChatStrategy<T> {

    var mOnItemClickListener: OnRecyclerViewItemClickListener<T>? = null

    var item : T ? = null
    fun bindEvent(onItemClickListener: OnRecyclerViewItemClickListener<T>?) {
        this.mOnItemClickListener = onItemClickListener
    }

    open fun onBind(holder: RecyclerView.ViewHolder, position: Int, item: T) {
        this.item = item
    }

    abstract fun onCreateHolder(binding: ViewDataBinding): RecyclerView.ViewHolder
}