package com.example.mylibrary.manager

import android.os.RemoteCallbackList
import com.example.mylibrary.listener.IMLoginStatusReceiver

/**
 * Create by kpa(billkp@yeah.net) on 2023/3/13
 * 16:29
 * Describe ：登陆管理器
 */
internal object IMLoginManager {

    private val mLoginListListener: RemoteCallbackList<IMLoginStatusReceiver> = RemoteCallbackList()


    internal fun registerLoginStatus(loginStatus: IMLoginStatusReceiver?) {
        mLoginListListener.register(loginStatus)
    }

    internal fun unRegisterLoginStatus(loginStatus: IMLoginStatusReceiver?) {
        mLoginListListener.unregister(loginStatus)
    }

    fun sendLoginStatus(status: Int) {
        val listenerCount = mLoginListListener.beginBroadcast()
        for (i in 0 until listenerCount) {
            mLoginListListener.getBroadcastItem(i)?.loginStatus(status)
        }
        mLoginListListener.finishBroadcast()
    }

}