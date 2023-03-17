package com.example.mylibrary

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.os.Parcel
import com.example.mylibrary.entities.IMClientOrder
import com.example.mylibrary.entities.IMLoginStatus
import com.example.mylibrary.entities.IMParams
import com.example.mylibrary.listener.ILongConnectionService
import com.example.mylibrary.listener.IMLoginStatusReceiver
import com.example.mylibrary.listener.IMMessageReceiver
import com.example.mylibrary.manager.IMLoginManager
import com.example.mylibrary.manager.IMMessageReceiveManager
import com.example.mylibrary.manager.NetworkManager
import com.example.mylibrary.utils.Logger

/**
 *
 * @author: kpa
 * @date: 2023/2/22
 * @description:
 */
internal class MessageService : Service() {

    private var mILongConnectionService: ILongConnectionService? = null
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

            override fun sendMessage(message: ByteArray) {
                mILongConnectionService?.sendMessage(message)
            }

            override fun sendOrder(order: Int) {
                when (order) {
                    IMClientOrder.CONNECT.ordinal -> {
                        mILongConnectionService?.connect()
                    }
                    IMClientOrder.DISCONNECT.ordinal -> {
                        mILongConnectionService?.disConnect()
                    }
                }
            }

            override fun login(imParams: IMParams?) {
                if (imParams != null) {
                    mILongConnectionService?.initLoginParams(imParams)
                } else {
                    throw NullPointerException("imParams is null")
                }
            }

            override fun registerMessageReceiveListener(messageReceiver: IMMessageReceiver?) {
                IMMessageReceiveManager.registerReceiver(messageReceiver)
            }

            override fun unRegisterMessageReceiveListener(messageReceiver: IMMessageReceiver?) {
                IMMessageReceiveManager.unRegisterReceiver(messageReceiver)
            }

            override fun registerLoginReceiveListener(loginStatusReceiver: IMLoginStatusReceiver?) {
                Logger.log("注册进程 ${Logger.getCurrentProcessName(this@MessageService)}")
                IMLoginManager.registerLoginStatus(loginStatusReceiver)
                IMLoginManager.sendLoginStatus(IMLoginStatus.CONNECT_DEFAULT.ordinal)
            }

            override fun unRegisterLoginReceiveListener(loginStatusReceiver: IMLoginStatusReceiver?) {
                IMLoginManager.unRegisterLoginStatus(loginStatusReceiver)
            }

            override fun bindLongConnectionService(service: com.example.mylibrary.listener.ILongConnectionService?) {
                mILongConnectionService = service
                service?.initLongConnection()
                if (service != null) {
                    NetworkManager.register(this@MessageService, service)
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Logger.log("Service 启动")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY // 或者返回 START_REDELIVER_INTENT
    }
}