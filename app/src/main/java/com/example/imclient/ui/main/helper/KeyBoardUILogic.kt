package com.example.imclient.ui.main.helper

import android.graphics.Rect
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.imclient.databinding.FragmentMainBinding
import com.example.imclient.utils.Logger
import com.example.imclient.utils.Utils


class KeyBoardUILogic(
    private val fragment: Fragment,
    private val inputBoxViewHolder: InputBoxViewHolder,
    private val binding: FragmentMainBinding
) {
    private var keyboardVisible = false

    private val listener = {
        val r = Rect()
        binding.root.getWindowVisibleDisplayFrame(r)
        val screenHeight = Utils.getHeight(fragment.context)
        val keyboardHeight = screenHeight - (r.bottom - r.top)
        val isKeyboardShowing: Boolean = keyboardHeight > (screenHeight / 3)
        if (isKeyboardShowing) {
            if (!keyboardVisible) {
                Log.i("KeyBoardUILogic", "显示键盘 registerKeyBoardListener : true")
                inputBoxViewHolder.setKeyBoardHeight(keyboardHeight)
                keyboardVisible = true
                fitKeyBoardActionCallback(true, keyboardHeight)
            }
        } else {
            if (keyboardVisible) {
                Log.i("KeyBoardUILogic", "不显示键盘 registerKeyBoardListener : false")
                keyboardVisible = false
                fitKeyBoardActionCallback(false, 0)
            }
        }
    }

    //键盘监听
    fun registerKeyBoardListener() {
        binding.root.post {
            binding.root.viewTreeObserver?.addOnGlobalLayoutListener(listener)
        }
    }

    fun unRegisterKeyBoardListener() {
        binding.root.post {
            binding.root.viewTreeObserver?.removeOnGlobalLayoutListener(null)
        }
    }

    private fun fitKeyBoardActionCallback(isShow: Boolean, keyboardHeight: Int) {
        if (isShow) {
            Logger.log("键盘弹出 $keyboardHeight")
        } else {
            Logger.log("键盘收起 $keyboardHeight")
            inputBoxViewHolder.softKeyBoardCloseEvent()
        }
    }
}