package com.example.mylibrary.interfaces

import android.content.Context
import com.example.mylibrary.entities.IMParams

/**
 * Create by kpa(billkp@yeah.net) on 2023/3/13
 * 16:58
 * Describe ：整个实现中其实我是不Core你到底使用websocket还是自己撸的TCP还是其他，
 * 我关心的是多进程中间件的实现。所以我们对这部分做一个
 * 抽象连接器
 */
interface LongConnectionService {
    /**
     * 初始化长连接实例
     */
    fun initLongConnection()

    /**
     * 连接长连接
     */
    fun connect()

    /**
     * 断开长连接
     */
    fun disConnect()

    /**
     * 重新连接长连接
     */
    fun reConnect()

    /**
     * 登录参数
     */
    fun initLoginParams(imParams: IMParams)

    /**
     * 发送消息
     */
    fun sendMessage(message: String)

    /**
     * 注册网络监听
     */
    fun registerNetwork(applicationContext: Context)
}