package com.example.imclient.ui.main.adapter.holder.strategy

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.imclient.base.OnRecyclerViewItemClickListener
import com.example.imclient.databinding.ImChatRecyclerReceiveImgItemBinding
import com.example.imclient.databinding.ImChatRecyclerReceiveTextItemBinding
import com.example.imclient.ui.main.entities.ChatEntity
import com.example.imclient.utils.safeCast

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description: 策略
 */
class ReceiveImgStrategy : IMMainChatStrategy<ChatEntity>() {


    override fun onBind(holder: RecyclerView.ViewHolder, position: Int, item: ChatEntity) {
        if (holder is ReceiveImgMessageHolder) {
            holder.bind(item)
        }
    }

    override fun onCreateHolder(
        binding: ViewDataBinding
    ): RecyclerView.ViewHolder {
        return ReceiveImgMessageHolder(binding.safeCast())
    }

    inner class ReceiveImgMessageHolder(val bind: ImChatRecyclerReceiveImgItemBinding) :
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