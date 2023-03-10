package com.example.mylibrary

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Parcel
import com.example.mylibrary.entities.IMClientOrder
import com.example.mylibrary.entities.IMLoginStatus
import com.example.mylibrary.entities.IMParams
import com.example.mylibrary.listener.IMLoginStatusReceiver
import com.example.mylibrary.listener.IMMessageReceiver
import com.example.mylibrary.manager.IMLoginManager
import com.example.mylibrary.manager.IMMessageReceiveManager
import com.example.mylibrary.utils.Logger

/**
 *
 * @author: kpa
 * @date: 2023/2/22
 * @description:
 */
internal class MessageService : Service() {

    override fun onBind(intent: Intent?): IBinder {
        if (checkCallingOrSelfPermission("com.example.mylibrary.permission.REMOTE_SERVICE_PERMISSION") == PackageManager.PERMISSION_DENIED) {
            throw RuntimeException("非法调用, 未添加正确权限")
        }
        return messageSender
    }

    private val messageSender by lazy {
        object : IMessageProvider.Stub() {
            override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
                var packageName: String? = null
                val packages: Array<String> =
                    packageManager.getPackagesForUid(getCallingUid()) ?: arrayOf()
                if (packages.isNotEmpty()) {
                    packageName = packages[0]
                }
                if (packageName == null || !packageName.startsWith("com.example")) {
                    Logger.log("权限校验失败  $packageName")
                    throw RuntimeException("非法调用 $packageName")
                }
                return super.onTransact(code, data, reply, flags)
            }

            override fun sendOrder(order: Int) {
                when (order) {
                    IMClientOrder.CONNECT.ordinal -> {
                        IMClient.with().getLongConnection().connect()
                    }
                    IMClientOrder.DISCONNECT.ordinal -> {
                        IMClient.with().getLongConnection().disConnect()
                    }
                }
            }

            override fun login(imParams: IMParams?) {
                if (imParams != null) {
                    IMClient.with().getLongConnection().initLoginParams(imParams)
                } else {
                    throw NullPointerException("imParams is null")
                }
            }

            override fun sendMessage(message: String?) {
                message?.let { IMClient.with().getLongConnection().sendMessage(it) }
            }

            override fun registerMessageReceiveListener(messageReceiver: IMMessageReceiver?) {
                IMMessageReceiveManager.registerReceiver(messageReceiver)
            }

            override fun unRegisterMessageReceiveListener(messageReceiver: IMMessageReceiver?) {
                IMMessageReceiveManager.unRegisterReceiver(messageReceiver)
            }

            override fun registerLoginReceiveListener(loginStatusReceiver: IMLoginStatusReceiver?) {
                IMLoginManager.registerLoginStatus(loginStatusReceiver)
                IMLoginManager.sendLoginStatus(IMLoginStatus.CONNECT_DEFAULT.ordinal)
            }

            override fun unRegisterLoginReceiveListener(loginStatusReceiver: IMLoginStatusReceiver?) {
                IMLoginManager.unRegisterLoginStatus(loginStatusReceiver)
            }

        }
    }

    override fun onCreate() {
        super.onCreate()
        IMClient.with().getLongConnection().registerNetwork(this)
        Logger.log("Service 启动")
    }

    /**
     * 模拟长链接，通知客户端消息
     */
//    inner class FakeTCPTask : Runnable {
//
//        override fun run() {
//            while (!serviceStop.get()) {
//                Thread.sleep(5000)
//                val messageModel = MessageModel().apply {
//                    from = "service"
//                    to = "client"
//                    content = "${System.currentTimeMillis()}"
//                }
//                val listenerCount = mListListener.beginBroadcast()
//                Logger.log("listener count = $listenerCount")
//                for (i in 0 until listenerCount) {
//                    mListListener.getBroadcastItem(i)?.onMessageReceived(messageModel)
//                }
//                mListListener.finishBroadcast()
//            }
//        }
//
//    }
//
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY // 或者返回 START_REDELIVER_INTENT
    }
}