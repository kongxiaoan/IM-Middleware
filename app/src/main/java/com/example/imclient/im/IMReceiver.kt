package com.example.imclient.im

import com.example.mylibrary.entities.MessageModel
import com.example.mylibrary.listener.IMMessageReceiver
import com.example.mylibrary.utils.Logger

/**
 *
 * @author: kpa
 * @date: 2023/2/22
 * @description:
 */
class IMReceiver : IMMessageReceiver.Stub() {
    override fun onMessageReceived(receiveMessage: MessageModel?) {
        Logger.log("客户端接收到的消息 $receiveMessage")
    }
}