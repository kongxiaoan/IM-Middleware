package com.example.imclient.ui.expression.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imclient.base.BaseRecyclerAdapter
import com.im.middleware.databinding.ImChatRecyclerMapItemBinding
import com.example.imclient.ui.expression.entities.MapBigExpressionEntity

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description:
 */
class MapBigExpressionAdapter :
    BaseRecyclerAdapter<MapBigExpressionEntity, MapBigExpressionAdapter.MapBigExpressionViewHolder>() {

    inner class MapBigExpressionViewHolder(val bind: ImChatRecyclerMapItemBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun bind(mapBigExpressionEntity: MapBigExpressionEntity) {
            bind.item = mapBigExpressionEntity
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapBigExpressionViewHolder {
        return MapBigExpressionViewHolder(
            ImChatRecyclerMapItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: MapBigExpressionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}