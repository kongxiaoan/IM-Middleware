package com.example.mylibrary.manager

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.mylibrary.MessageService
import com.example.mylibrary.entities.IMLoginStatus
import com.example.mylibrary.listener.ILongConnectionService
import com.example.mylibrary.utils.Logger

/**
 *
 * @author: kpa
 * @date: 2023/3/17
 * @description:
 */
@SuppressLint("StaticFieldLeak")
internal object NetworkManager {
    private var mILongConnectionService: ILongConnectionService? = null
    private var mContext: Context? = null
    fun register(context: Context, mILongConnectionService: ILongConnectionService) {
        this.mContext = context
        this.mILongConnectionService = mILongConnectionService
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        Logger.log("网络注册 ")
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
        mILongConnectionService?.connect()
    }

    private fun closeWebSocket() {
        // 关闭 WebSocket 的代码
        Logger.log("无网络 断开连接")
        Logger.log("注册进程 ${mContext?.let { Logger.getCurrentProcessName(it) }}")
        IMLoginManager.sendLoginStatus(IMLoginStatus.CONNECT_FAIL.ordinal)
        mILongConnectionService?.disConnect()
    }
}