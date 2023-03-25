package com.example.imclient.utils

import android.util.Log

object Logger {

    const val TAG = "X-IM-MIDDLEWARE"

    fun log(contnet: String) {
        Log.d(TAG, contnet)
    }
}