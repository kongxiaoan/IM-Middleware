package com.example.imclient.ui.main.adapter.holder.strategy

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.imclient.databinding.ImChatRecyclerSendTextItemBinding
import com.example.imclient.ui.main.entities.ChatEntity
import com.example.imclient.utils.Logger
import com.example.imclient.utils.safeCast

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description: 发送文字策略
 */
class SendTextStrategy : IMMainChatStrategy<ChatEntity>() {

    override fun onBind(holder: RecyclerView.ViewHolder, position: Int, chatEntity: ChatEntity) {
        if (holder is SendTextMessageHolder) {
            Logger.log("chatEntity SendTextStrategy")
            holder.bind(chatEntity)
        }
    }

    override fun onCreateHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return SendTextMessageHolder(binding.safeCast())
    }

    inner class SendTextMessageHolder(val bind: ImChatRecyclerSendTextItemBinding) :
        RecyclerView.ViewHolder(bind.root) {

        init {
            bind.sdvAvatar.setOnClickListener {
                item?.let { item ->
                    mOnItemClickListener?.onItemClick(
                        it,
                        item,
                        adapterPosition
                    )
                }
            }
        }
        fun bind(chatEntity: ChatEntity) {
            bind.item = chatEntity
        }
    }
}