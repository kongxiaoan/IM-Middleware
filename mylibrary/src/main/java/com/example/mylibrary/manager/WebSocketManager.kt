package com.example.mylibrary.manager

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.mylibrary.IMClient
import com.example.mylibrary.entities.IMLoginStatus
import com.example.mylibrary.entities.IMParams
import com.example.mylibrary.entities.MessageModel
import com.example.mylibrary.utils.Logger
import kotlinx.coroutines.*
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit


/**
 *
 * @author: kpa
 * @date: 2023/3/7
 * @description: socket 管理器
 */
internal object WebSocketManager {
    private const val WS_URL = "ws://192.168.31.222:8080"
    private const val heartbeatInterval = 30000L
    private var isAuthorized = false
    private var imParams: IMParams? = null
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
        if (imParams != null) {
            val request = Request.Builder()
                .url(WS_URL)
                .build()
            httpClient.newWebSocket(request, wsListener)
        }
    }

    fun login(imParams: IMParams) {
        WebSocketManager.imParams = imParams
    }

    fun release() {
        disconnect()
    }

    fun sendMessage(message: String) {
        if (mWebSocket != null) {
            mWebSocket?.send(message)
        } else {
            // WebSocket 未连接
            // 处理未连接时的情况
            val messageModel = MessageModel().apply {
                from = "service"
                to = "client"
                content = "socket未连接"
            }
            IMMessageReceiveManager.onReceive(messageModel)
        }
    }

    fun disconnect() {
        mWebSocket?.close(1000, "Disconnect")
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun startHeartbeat(webSocket: WebSocket) {
        GlobalScope.launch {
            while (true) {
                delay(heartbeatInterval)
                Logger.log("发送心跳 isAuthorized = $isAuthorized")
                if (!isAuthorized) {
                    // 如果未经授权，则无法发送心跳
                    break
                }
                if (!webSocket.send("pang")) {
                    // 如果发送失败，则关闭连接
                    webSocket.close(1000, "Heartbeat send failed")
                    break
                }
            }
        }
    }

    private val wsListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            Logger.log("连接成功")
            mWebSocket = webSocket
            webSocket.send(imParams?.token!!)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Logger.log("onMessage text $text ")
            if (!isAuthorized) {
                // 如果没有身份验证，则将服务器返回的消息解析为身份验证结果
                isAuthorized = text == "authorized"
                if (isAuthorized) {
                    // 如果验证成功，则开始心跳
                    startHeartbeat(webSocket)
                } else {
                    // 如果验证失败，则关闭连接
                    webSocket.close(1000, "Unauthorized")
                }
            } else {
                // 如果已经验证，则处理来自服务器的消息
                println("Received message: $text")
            }

            val messageModel = MessageModel().apply {
                from = "service"
                to = "client"
                content = text
            }
            IMMessageReceiveManager.onReceive(messageModel)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            Logger.log("onMessage text $bytes")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            val messageModel = MessageModel().apply {
                from = "service"
                to = "client"
                content = reason
            }
            isAuthorized = false
            IMMessageReceiveManager.onReceive(messageModel)
            IMLoginManager.sendLoginStatus(IMLoginStatus.CONNECT_FAIL.ordinal)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            isAuthorized = false
            val messageModel = MessageModel().apply {
                from = "service"
                to = "client"
                content = reason
            }
            IMMessageReceiveManager.onReceive(messageModel)
            IMLoginManager.sendLoginStatus(IMLoginStatus.CONNECT_FAIL.ordinal)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            isAuthorized = false
            IMLoginManager.sendLoginStatus(IMLoginStatus.CONNECT_FAIL.ordinal)
            val messageModel = MessageModel().apply {
                from = "service"
                to = "client"
                content = t.message
            }
            IMMessageReceiveManager.onReceive(messageModel)
        }
    }

    fun registerNetwork(applicationContext: Context) {
        val connectivityManager: ConnectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private val networkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            // 网络连接成功时执行
            connectWebSocket() // 重连 WebSocket
        }

        override fun onLost(network: Network) {
            // 网络连接断开时执行
            closeWebSocket() // 关闭 WebSocket
        }
    }

    private fun connectWebSocket() {
        // 连接 WebSocket 的代码
        connect()
    }

    private fun closeWebSocket() {
        // 关闭 WebSocket 的代码
        disconnect()
    }

}