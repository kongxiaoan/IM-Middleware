package com.example.mylibrary.manager

import android.os.RemoteCallbackList
import com.example.mylibrary.IMClient
import com.example.mylibrary.entities.MessageModel
import com.example.mylibrary.listener.IMMessageReceiver

/**
 * Create by kpa(billkp@yeah.net) on 2023/3/13
 * 16:33
 * Describe ：（接收）消息管理器
 */
internal object IMMessageReceiveManager {
    private val mListListener: RemoteCallbackList<IMMessageReceiver> = RemoteCallbackList()


    internal fun registerReceiver(messageReceiver: IMMessageReceiver?) {
        mListListener.register(messageReceiver)
    }

    internal fun unRegisterReceiver(messageReceiver: IMMessageReceiver?) {
        mListListener.unregister(messageReceiver)
    }

    fun onReceive(messageModel: MessageModel) {
        val listenerCount = mListListener.beginBroadcast()
        for (i in 0 until listenerCount) {
            mListListener.getBroadcastItem(i)?.onMessageReceived(messageModel)
        }
        mListListener.finishBroadcast()
    }

}