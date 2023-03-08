package com.example.mylibrary

import android.app.ActivityManager
import android.content.Context
import android.os.Process
import com.example.mylibrary.entities.MessageModel
import com.example.mylibrary.entities.IMLoginStatus
import com.example.mylibrary.utils.Logger
import okhttp3.*
import java.util.concurrent.TimeUnit


/**
 *
 * @author: kpa
 * @date: 2023/3/7
 * @description:
 */
object WebSocketManager {
    private const val WS_URL = "ws://192.168.31.222:8080"

    private val httpClient by lazy {
        OkHttpClient().newBuilder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .pingInterval(40, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    private var mWebSocket: WebSocket? = null

    public fun connect() {
        val request = Request.Builder()
            .url(WS_URL)
            .build()
        mWebSocket = httpClient.newWebSocket(request, wsListener)
    }

    fun release() {
        mWebSocket?.cancel()
    }

    fun send(message: String) {
        mWebSocket?.send(message)
    }

    /**
     * 获取当前进程名
     *
     * @param context 上下文
     * @return
     */
    fun getCurProcessName(context: Context): String? {
        // 获取此进程的标识符
        val pid = Process.myPid()
        // 获取活动管理器
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        // 从应用程序进程列表找到当前进程，是：返回当前进程名
        for (appProcess in activityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }


    private val wsListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            Logger.log("onOpen ${webSocket == null}")
            mWebSocket = webSocket
            IMClient.sendLoginStatus(IMLoginStatus.CONNECT_SUCCESS.ordinal)
            Logger.log("onOpen ${mWebSocket == null}")
            webSocket.send(
                "我是客户端代码发送的 ${
                    IMClient?.mApplication?.applicationContext?.let {
                        getCurProcessName(
                            it
                        )
                    }
                }"
            )
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Logger.log("onMessage text $text")
            val messageModel = MessageModel().apply {
                from = "service"
                to = "client"
                content = "${System.currentTimeMillis()}"
            }
            IMClient.onReceive(messageModel)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            IMClient.sendLoginStatus(IMLoginStatus.CONNECT_FAIL.ordinal)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            IMClient.sendLoginStatus(IMLoginStatus.CONNECT_FAIL.ordinal)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            IMClient.sendLoginStatus(IMLoginStatus.CONNECT_FAIL.ordinal)
            Logger.log("onFailure " + t.localizedMessage)
        }
    }


}