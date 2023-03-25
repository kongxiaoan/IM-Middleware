package com.example.imclient.ui.main.adapter.holder.strategy

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.im.middleware.databinding.ImChatRecyclerSendImgItemBinding
import com.example.imclient.ui.main.entities.ChatEntity
import com.example.imclient.utils.safeCast

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description: 发送图片策略
 */
class SendImgStrategy : IMMainChatStrategy<ChatEntity>() {

    override fun onBind(holder: RecyclerView.ViewHolder, position: Int, item: ChatEntity) {
        if (holder is SendImgMessageHolder) {
            holder.bind(item)
        }
    }

    override fun onCreateHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return SendImgMessageHolder(binding.safeCast())
    }

    inner class SendImgMessageHolder(val bind: ImChatRecyclerSendImgItemBinding) :
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