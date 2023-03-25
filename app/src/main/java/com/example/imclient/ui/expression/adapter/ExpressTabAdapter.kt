package com.example.imclient.ui.expression.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imclient.R
import com.example.imclient.base.BaseRecyclerAdapter
import com.example.imclient.databinding.ImChatExpressionTabItemBinding
import com.example.imclient.ui.expression.entities.ExpressTabEntity

/**
 *
 * @author: kpa
 * @date: 2023/3/24
 * @description:
 */
class ExpressTabAdapter :
    BaseRecyclerAdapter<ExpressTabEntity, ExpressTabAdapter.ExpressTabViewHolder>() {

    // 默认选中0
    var selectedPosition = 0

    inner class ExpressTabViewHolder(val bind: ImChatExpressionTabItemBinding) :
        RecyclerView.ViewHolder(bind.root) {

        init {
            bind.expressionBG.setOnClickListener {
                if (adapterPosition >= 0 && adapterPosition < list.size) {
                    mOnItemClickListener?.onItemClick(it, getItem(adapterPosition), adapterPosition)
                    notifyDataSetChanged()
                }
            }
        }


        fun bind(item: ExpressTabEntity, position: Int) {
            bind.expressItemIV.setImageResource(item.icon)
            bind.expressionBG.setBackgroundResource(if (position == selectedPosition) R.drawable.im_panel_picture_bg else 0)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpressTabViewHolder {
        return ExpressTabViewHolder(
            ImChatExpressionTabItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExpressTabViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}