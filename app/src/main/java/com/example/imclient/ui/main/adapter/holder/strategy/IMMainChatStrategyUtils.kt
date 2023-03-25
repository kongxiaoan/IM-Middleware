package com.example.imclient.ui.main.adapter.holder.strategy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imclient.base.OnRecyclerViewItemClickListener
import com.im.middleware.databinding.ImChatRecyclerSendTextItemBinding
import com.im.middleware.databinding.ImChatRecyclerEmptyItemBinding
import com.im.middleware.databinding.ImChatRecyclerReceiveImgItemBinding
import com.im.middleware.databinding.ImChatRecyclerReceiveTextItemBinding
import com.im.middleware.databinding.ImChatRecyclerReceiveVoiceLayoutBinding
import com.im.middleware.databinding.ImChatRecyclerSendImgItemBinding
import com.im.middleware.databinding.ImChatRecyclerSendVoiceLayoutBinding
import com.example.imclient.function.utils.ChatHolderType
import com.example.imclient.ui.main.entities.ChatEntity
import com.example.imclient.utils.Logger

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description: 策略类管理
 */
object IMMainChatStrategyUtils {

    @JvmStatic
    val strategies = mutableMapOf<Int, IMMainChatStrategy<ChatEntity>>()

    fun onCreateViewHolder(
        parent: ViewGroup,
        type: Int,
        onItemClickListener: OnRecyclerViewItemClickListener<ChatEntity>?
    ): RecyclerView.ViewHolder {
        return when (type) {
            ChatHolderType.SEND_TEXT.type -> {
                val sendTextStrategy = SendTextStrategy().apply {
                    bindEvent(onItemClickListener)
                }
                strategies[type] = sendTextStrategy
                Logger.log("chatEntity onCreateViewHolder")
                sendTextStrategy.onCreateHolder(
                    ImChatRecyclerSendTextItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent,
                        false
                    )
                )
            }
            ChatHolderType.RECEIVE_TEXT.type -> {
                val receiveTextStrategy = ReceiveTextStrategy().apply {
                    bindEvent(onItemClickListener)
                }
                strategies[type] = receiveTextStrategy
                receiveTextStrategy.onCreateHolder(
                    ImChatRecyclerReceiveTextItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent,
                        false
                    )
                )
            }
            ChatHolderType.SEND_IMG.type -> {
                val sendImgStrategy = SendImgStrategy().apply {
                    bindEvent(onItemClickListener)
                }
                strategies[type] = sendImgStrategy
                sendImgStrategy.onCreateHolder(
                    ImChatRecyclerSendImgItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent,
                        false
                    )
                )
            }
            ChatHolderType.RECEIVE_IMG.type -> {
                val receiveImgStrategy = ReceiveImgStrategy().apply {
                    bindEvent(onItemClickListener)
                }
                strategies[type] = receiveImgStrategy
                receiveImgStrategy.onCreateHolder(
                    ImChatRecyclerReceiveImgItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent,
                        false
                    )
                )
            }
            ChatHolderType.SEND_VOICE.type -> {
                val sendVoiceStrategy = SendVoiceStrategy().apply {
                    bindEvent(onItemClickListener)
                }
                strategies[type] = sendVoiceStrategy
                sendVoiceStrategy.onCreateHolder(
                    ImChatRecyclerSendVoiceLayoutBinding.inflate(
                        LayoutInflater.from(parent.context), parent,
                        false
                    )
                )
            }
            ChatHolderType.RECEIVE_VOICE.type -> {
                val receiveVoiceStrategy = ReceiveVoiceStrategy().apply {
                    bindEvent(onItemClickListener)
                }
                strategies[type] = receiveVoiceStrategy
                receiveVoiceStrategy.onCreateHolder(
                    ImChatRecyclerReceiveVoiceLayoutBinding.inflate(
                        LayoutInflater.from(parent.context), parent,
                        false
                    )
                )
            }
            else -> {
                val emptyMessageHolder = SystemEmptyMessageHolder().apply {
                    bindEvent(onItemClickListener)
                }
                strategies[type] = emptyMessageHolder
                emptyMessageHolder.onCreateHolder(
                    ImChatRecyclerEmptyItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }
}