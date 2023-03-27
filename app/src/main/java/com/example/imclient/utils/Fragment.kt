package com.example.imclient.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.emoji.text.EmojiCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.imclient.MyApplication
import com.example.imclient.ui.main.IMMainViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description:
 */

inline fun <reified T : ViewModel> Fragment.viewModel(
    vm: T,
    body: T.() -> Unit
): T {
    vm.body()
    return vm
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))


fun <T : Any, L : Flow<T>> LifecycleOwner.observe(flowData: L, body: (T?) -> Unit) =
    flowData.onEach(body).launchIn(lifecycleScope)

fun <T : Any, L : Flow<T>> ViewModel.observe(flowData: L, body: (T?) -> Unit) =
    flowData.onEach(body).launchIn(this.viewModelScope)


//判断当前 EmojiCompat 是否初始化成功
fun isEmojiCompatInit(): Boolean {
    return EmojiCompat.get().loadState == EmojiCompat.LOAD_STATE_SUCCEEDED
}

//获取可兼容的 emoji 字符串
fun String.getCompatEmojiString(): CharSequence? {
    //将当前 code 转换为 16 进制数
    val hex = this.toInt(16)
    //将当前 16 进制数转换成字符数组
    val chars = Character.toChars(hex)
    //将当前字符数组转换成 TextView 可加载的 String 字符串
    val mEmojiString = String(chars)
    //判断当前系统是否大于等于 19，并且 EmojiCompat 初始化成功
    return if (isEmojiCompatInit()) {
        EmojiCompat.get().process(mEmojiString)
    } else mEmojiString
}

fun Float.dpToPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this, MyApplication.context.resources.displayMetrics
    )
}

fun Int.dpToPx(): Int {
    return this.toFloat().dpToPx().toInt()
}
