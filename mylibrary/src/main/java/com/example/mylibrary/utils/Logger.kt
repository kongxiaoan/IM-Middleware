package com.example.mylibrary.utils

import android.app.ActivityManager
import android.content.Context
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

    fun getCurrentProcessName(context: Context): String {
        val pid = android.os.Process.myPid()
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (process in manager.runningAppProcesses) {
            if (process.pid == pid) {
                return process.processName
            }
        }
        return ""
    }

    fun log(contnet: String) {
        logLiveData.postValue(contnet)
        Log.d(TAG, contnet)
    }
}
