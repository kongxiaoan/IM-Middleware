package com.example.imclient.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imclient.base.BaseRecyclerAdapter
import com.example.imclient.ui.main.adapter.holder.strategy.IMMainChatStrategyUtils
import com.example.imclient.ui.main.entities.ChatEntity

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description: 聊天适配器
 */
class IMMainAdapter : BaseRecyclerAdapter<ChatEntity, RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return IMMainChatStrategyUtils.onCreateViewHolder(parent, viewType, mOnItemClickListener)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        var chatEntity = getItem(position)
        if (payloads.isNotEmpty()) {
            //当 RecyclerView 中的一个项需要更新时，调用此方法。payloads 参数是一个列表，其中包含应该更新的对象
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        IMMainChatStrategyUtils.strategies[item.chatType.type]?.run {
            onBind(holder, position, item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val chatEntity = getItem(position)
        return chatEntity.chatType.type
    }


}