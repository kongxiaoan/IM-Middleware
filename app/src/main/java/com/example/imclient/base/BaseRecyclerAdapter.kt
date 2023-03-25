package com.example.imclient.base

import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description:
 */
abstract class BaseRecyclerAdapter<T, H : RecyclerView.ViewHolder> : RecyclerView.Adapter<H>() {

    private val mList = LinkedList<T>()

    var mOnItemClickListener: OnRecyclerViewItemClickListener<T>? = null

    /**
     * 获取列表数据
     */
    val list: List<T>
        get() = mList

    fun registerOnItemClickListener(onItemClickListener: OnRecyclerViewItemClickListener<T>) {
        mOnItemClickListener = onItemClickListener
    }

    fun unRegisterOnItemClickListener() {
        mOnItemClickListener = null
    }

    fun appendToTop(t: T?) {
        if (t == null) {
            return
        }
        mList.add(0, t)
        notifyItemInserted(0)
    }

    fun append(t: T?) {
        if (t == null) {
            return
        }
        mList.add(t)
        notifyItemInserted(mList.size)
    }

    fun addAll(t: List<T>) {
        mList.clear()
        mList.addAll(t)
        notifyDataSetChanged()
    }

    fun append(position: Int, item: T?) {
        if (item == null || position < 0 || position >= mList.size) return
        mList.add(position, item)
        notifyItemInserted(position)
    }

    fun replaceItem(position: Int, item: T) {
        if (position < 0 || position >= mList.size) return
        mList[position] = item
        notifyItemChanged(position)
    }

    fun replaceItemPayloads(position: Int, item: T) {
        if (position < 0 || position >= mList.size) return
        mList[position] = item
        notifyItemChanged(position, "payloads")
    }


    fun removeBottom() {
        if (mList.size > 0) {
            mList.removeAt(mList.size - 1)
            notifyDataSetChanged()
        }
    }

    fun removeByIndex(index: Int) {
        if (index < 0 || index >= mList.size) return
        mList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun removeAll() {
        mList.clear()
        notifyDataSetChanged()
    }

    /**
     * 追加数据
     *
     * @param list
     */
    fun appendToList(list: List<T>?) {
        if (list == null) {
            return
        }
        mList.addAll(list)
        notifyItemRangeInserted(itemCount, list.size)
    }


    /**
     * 追加数据
     *
     * @param list
     */
    fun appendToTopList(list: List<T>?) {
        if (list == null) {
            return
        }
        mList.addAll(0, list)
        notifyDataSetChanged()
    }

    /**
     * 清空数据
     */
    fun clear() {
        mList.clear()
        notifyDataSetChanged()
    }


    fun deleteAll() {
        mList.clear()
        notifyDataSetChanged()
    }

    fun replace(list: List<T>?) {
        if (list == null) {
            return
        }
        mList.clear()
        mList.addAll(0, list)
        notifyDataSetChanged()
    }

    // 替换数据不刷新adapter
    fun replaceWithoutNotify(list: List<T>?) {
        if (list == null) {
            return
        }
        mList.clear()
        mList.addAll(0, list)
    }

    /**
     * 获取数量
     */
    override fun getItemCount(): Int {
        return mList.size
    }

    /**
     * 根据position 获取单个对象
     */
    fun getItem(position: Int): T {
        return if (position > mList.size - 1) {
            throw Exception("Out of index")
        } else mList[position]
    }

    /**
     * 获取position
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}