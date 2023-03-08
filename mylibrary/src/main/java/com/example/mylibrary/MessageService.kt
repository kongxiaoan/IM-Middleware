package com.example.mylibrary

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Parcel
import com.example.mylibrary.entities.IMLoginStatus
import com.example.mylibrary.listener.IMLoginStatusReceiver
import com.example.mylibrary.listener.IMMessageReceiver
import com.example.mylibrary.utils.Logger
import java.util.concurrent.atomic.AtomicBoolean

/**
 *
 * @author: kpa
 * @date: 2023/2/22
 * @description:
 */
class MessageService : Service() {

    private val serviceStop: AtomicBoolean = AtomicBoolean()
    override fun onBind(intent: Intent?): IBinder? {
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
                when(order) {
                    0 -> {
                        WebSocketManager.connect()
                    }
                    1 -> {
                        WebSocketManager.release()
                    }
                }
            }

            override fun sendMessage(message: String?) {
                message?.let { WebSocketManager.sendMessage(it) }
            }

            override fun registerMessageReceiveListener(messageReceiver: IMMessageReceiver?) {
                IMClient.registerReceiver(messageReceiver)
            }

            override fun unRegisterMessageReceiveListener(messageReceiver: IMMessageReceiver?) {
                IMClient.unRegisterReceiver(messageReceiver)
            }

            override fun registerLoginReceiveListener(loginStatusReceiver: IMLoginStatusReceiver?) {
                IMClient.registerLoginStatus(loginStatusReceiver)
                IMClient.sendLoginStatus(IMLoginStatus.CONNECT_DEFAULT.ordinal)
            }

            override fun unRegisterLoginReceiveListener(loginStatusReceiver: IMLoginStatusReceiver?) {
                IMClient.unRegisterLoginStatus(loginStatusReceiver)
            }

        }
    }

    override fun onCreate() {
        super.onCreate()
        WebSocketManager.registerNetwork(this)
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