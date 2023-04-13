package com.example.imclient.utils

import android.util.Log

object Logger {

    const val TAG = "X-IM-MIDDLEWARE"

    fun log(contnet: String, tag: String = TAG) {
        Log.d(tag, contnet)
    }
}
