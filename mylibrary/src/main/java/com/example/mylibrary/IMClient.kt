package com.example.mylibrary

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteCallbackList
import com.example.mylibrary.entities.IMClientOrder
import com.example.mylibrary.entities.IMParams
import com.example.mylibrary.entities.MessageModel
import com.example.mylibrary.listener.IMLoginStatusReceiver
import com.example.mylibrary.listener.IMMessageReceiver
import com.example.mylibrary.utils.Logger

/**
 *
 * @author: kpa
 * @date: 2023/2/22
 * @description:
 */
object IMClient {

    private val deathRecipient by lazy {
        object : IBinder.DeathRecipient {
            override fun binderDied() {
                Logger.log("被杀死 ${messageSender != null}")
                if (messageSender != null) {
                    messageSender?.asBinder()?.unlinkToDeath(this, 0)
                    messageSender = null
                }
                setupService()
            }
        }
    }
    var mApplication: Context? = null

    private var messageSender: IMessageProvider? = null

    internal var mReceiver: IMMessageReceiver.Stub? = null

    private var imParams: IMParams? = null

    internal var loginCallback: IMLoginStatusReceiver.Stub? = null


    @JvmStatic
    fun init(
        application: Application,
        imParams: IMParams,
        loginStatusCallback: IMLoginStatusReceiver.Stub,
        receiver: IMMessageReceiver.Stub
    ) {
        mApplication = application
        mReceiver = receiver
        this.imParams = imParams
        this.loginCallback = loginStatusCallback
        setupService()
    }

    fun send(message: String) {
        messageSender?.sendMessage(message)
    }

    fun connect() {
        messageSender?.sendOrder(IMClientOrder.CONNECT.ordinal)
    }

    fun disConnect() {
        messageSender?.sendOrder(IMClientOrder.DISCONNECT.ordinal)
    }

    @JvmStatic
    fun loginOut() {
        messageSender?.run {
            if (this.asBinder().isBinderAlive) {
                unRegisterMessageReceiveListener(mReceiver)
                unRegisterLoginReceiveListener(loginCallback)
            }
        }
        mApplication?.run {
            unbindService(iServiceConnection)
        }
    }


    private fun setupService() {
        mApplication?.applicationContext?.run {
            val intent = Intent(this, MessageService::class.java)
            //希望unbind后Service仍保持运行，这样的情况下，可以同时调用bindService和startService（比如像本例子中的消息服务，退出UI进程，Service仍需要接收到消息）
            bindService(intent, iServiceConnection, Context.BIND_AUTO_CREATE)
            startService(intent)
            //连接Socket
        }
    }


    private val iServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            messageSender = IMessageProvider.Stub.asInterface(service)
            Logger.log("进程连接成功 $name")
            messageSender?.run {
                asBinder().linkToDeath(deathRecipient, 0)
                registerMessageReceiveListener(mReceiver)
                registerLoginReceiveListener(loginCallback)
                login(imParams)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Logger.log("进程连接断开 $name")
        }

    }

}