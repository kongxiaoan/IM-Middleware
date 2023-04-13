package com.example.imclient.ui.expression.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imclient.base.BaseRecyclerAdapter
import com.example.imclient.databinding.ImChatRecyclerEmojiItemBinding
import com.example.imclient.ui.expression.entities.EmojiEntry
import com.example.imclient.utils.Logger
import com.example.imclient.utils.getCompatEmojiString

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description:
 */
class EmojiAdapter() :
    BaseRecyclerAdapter<EmojiEntry, EmojiAdapter.EmojiViewHolder>() {

    private var context: Context? = null

    inner class EmojiViewHolder(val bind: ImChatRecyclerEmojiItemBinding) :
        RecyclerView.ViewHolder(bind.root) {
        init {
            bind.emojiTextView.setOnClickListener {
                Logger.log("position $adapterPosition list = ${list.size}")
                if (adapterPosition >= 0 && adapterPosition < list.size) {
                    mOnItemClickListener?.onItemClick(it, getItem(adapterPosition), adapterPosition)
                }
            }
        }

        fun bind(emojiEntry: EmojiEntry) {
            bind.emojiTextView.text = emojiEntry.emoji.getCompatEmojiString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiViewHolder {
        context = parent.context

        return EmojiViewHolder(
            ImChatRecyclerEmojiItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
