package com.example.mylibrary.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData

/**
 *
 * @author: kpa
 * @date: 2023/2/22
 * @description: 日志将提供腾讯的xlog 进行IM详细的单独存储
 */
object Logger {

    const val TAG = "IM_LOGGER"

    public val logLiveData: MutableLiveData<String> = MutableLiveData()

    fun log(contnet: String) {
        logLiveData.postValue(contnet)
        Log.d(TAG, contnet)
    }
}