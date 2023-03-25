package com.example.imclient.utils

import android.content.Context
import android.view.WindowManager

/**
 *
 * @author: kpa
 * @date: 2023/3/22
 * @description:
 */
object Utils {

    /***
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    fun getHeight(context: Context?): Int {
        if (context == null) {
            return 0
        }
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        return display.height
    }
}