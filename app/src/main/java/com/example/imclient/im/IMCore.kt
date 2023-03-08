package com.example.imclient.im

import android.app.Application
import com.example.mylibrary.IMClient
import com.example.mylibrary.entities.IMParams
import com.example.mylibrary.listener.IMLoginStatusReceiver
import com.example.mylibrary.utils.Logger

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
        IMClient.init(application, IMParams.Builder().build(), object : IMLoginStatusReceiver.Stub() {
            override fun loginStatus(status: Int) {
                Logger.log("登录状态 $status")
            }
        }, IMReceiver())
    }

    /**
     * 发送消息
     */
    fun send(message: String) {
        IMClient.send(message)
    }

    /**
     * 登出
     */
    fun logOut() {
        IMClient.loginOut()
    }
}