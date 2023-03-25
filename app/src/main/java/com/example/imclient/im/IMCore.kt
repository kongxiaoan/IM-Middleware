package com.example.imclient.im

import android.app.Application
import com.example.mylibrary.IMClient
import com.example.mylibrary.IMClientEnum
import com.example.mylibrary.IMClientParamsOuterClass
import com.example.mylibrary.IMClientParamsOuterClass.IMClientParams
import com.example.mylibrary.IMClientParamsOuterClass.TextMessage
import com.example.mylibrary.entities.IMParams
import com.example.mylibrary.listener.IMLoginStatusReceiver
import com.example.mylibrary.utils.Logger
import com.example.mylibrary.utils.XConverters
import com.google.protobuf.ByteString

/**
 *
 * @author: kpa
 * @date: 2023/2/22
 * @description:
 */
object IMCore {

    /**
     * 初始化IM
     */
    fun init(application: Application) {
        Logger.log("IM 初始化")
        IMClient.Builder()
            .build()
        IMClient.with().init(
            application,
            IMParams.Builder()
                .withUrl("ws://192.168.31.222:8081")
                .withToken("auth")
                .build(),
            object : IMLoginStatusReceiver.Stub() {
                override fun loginStatus(status: Int) {
                    when (status) {
                        0 -> {
                            Logger.log("正在连接websocket")
                        }
                        1 -> {
                            Logger.log("连接成功")
                        }
                        2 -> {
                            Logger.log("断开连接或失败")
                        }
                    }
                }
            },
            IMReceiver()
        )
    }

    /**
     * 发送消息
     */
    fun send(message: String) {
        // 构建文本消息
        val textMessage = TextMessage.newBuilder().setContent("我是文本消息").build()
        val params = IMClientParams.newBuilder()
            //发送的是用户消息
            .setType(IMClientEnum.PlatformMsgType.USER_MSG_TYPE_VALUE)
            // 发送的是文本消息
            .setCmd(IMClientEnum.IMClientCMDEnum.CHAT_TXT_CMD_VALUE)
            //发送者ID
            .setSendId("1011011")
            //接收者
            .setChatWithId("102094")
            //发送的内容
            .setBody(textMessage.toByteString())
            //消息唯一ID
            .setMsgId(100)
            //发送时间
            .setSendTime(System.currentTimeMillis())
            .build()
        IMClient.with().send(params.toByteArray())
    }


    /**
     * 登出
     */
    fun logOut() {
        IMClient.with().loginOut()
    }
}