package com.example.imclient.base

import android.view.View

interface OnRecyclerViewItemClickListener<T> {
    fun onItemClick(v: View, t: T, position: Int)
}
