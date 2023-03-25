package com.example.imclient.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.im.middleware.R

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description: DataBind相关扩展
 */

/**
 * 用于安全类型强转
 */
inline fun <reified T> ViewDataBinding.safeCast(): T {
    if (this is T) {
        return this as T
    }
    throw ClassCastException("Cannot cast $this to ${T::class.java.simpleName}")
}

inline fun <reified T> Any.safeCast(): T? = this as? T

@BindingAdapter("loadImage", "gender")
inline fun loadImage(view: ImageView, url: String?, gender: Int) {
    Glide.with(view.context)
        .load(url)
        .apply(RequestOptions.placeholderOf(if (gender == 1) R.drawable.icon_default_female else R.drawable.icon_default_male))
        .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
        .into(view)
}

@BindingAdapter("loadImage")
inline fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
        .into(view)
}