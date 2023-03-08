package com.example.mylibrary.utils

import android.util.Log

/**
 *
 * @author: kpa
 * @date: 2023/2/22
 * @description:
 */
object Logger {

    const val TAG = "IM_LOGGER"

    fun log(contnet: String) {
        Log.d(TAG, contnet)
    }
}