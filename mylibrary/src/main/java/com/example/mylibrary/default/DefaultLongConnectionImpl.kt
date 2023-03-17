package com.example.mylibrary.default

import com.example.mylibrary.entities.IMLoginStatus
import com.example.mylibrary.entities.IMParams
import com.example.mylibrary.entities.MessageModel
import com.example.mylibrary.listener.ILongConnectionService
import com.example.mylibrary.manager.IMLoginManager
import com.example.mylibrary.manager.IMMessageReceiveManager
import com.example.mylibrary.utils.Logger
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

/**
 * Create by kpa(billkp@yeah.net) on 2023/3/13
 * 17:44
 * Describe ：注释说明信息
 */
class DefaultLongConnectionImpl : ILongConnectionService.Stub() {
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
    private val heartbeatInterval = 30000L
    private var isAuthorized = false
    private var imParams: IMParams? = null

    override fun initLongConnection() {

    }

    fun release() {
        disConnect()
    }

    override fun connect() {
        Logger.log("开始连接 ${imParams}")
        if (imParams != null) {
            val request = Request.Builder()
                .url(imParams!!.url)
                .build()
            httpClient.newWebSocket(request, wsListener)
        }
    }

    override fun disConnect() {
        mWebSocket?.close(1000, "Disconnect")
    }

    override fun reConnect() {
        TODO("Not yet implemented")
    }

    override fun initLoginParams(imParams: IMParams) {
        this.imParams = imParams
    }

    override fun sendMessage(message: String) {
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

    /**
     * 注册网络监听
     */
    override fun registerNetwork() {

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun startHeartbeat(webSocket: WebSocket) {
        GlobalScope.launch {
            while (true) {
                delay(heartbeatInterval)
                Logger.log("发送心跳 isAuthorized = $isAuthorized")
                if (!isAuthorized) {
                    // 如果未经授权，则无法发送心跳
                    IMLoginManager.sendLoginStatus(IMLoginStatus.CONNECT_FAIL.ordinal)
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
}