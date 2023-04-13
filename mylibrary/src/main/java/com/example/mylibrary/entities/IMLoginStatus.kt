package com.example.mylibrary.entities

/**
 *
 * @author: kpa
 * @date: 2023/3/7
 * @description: 登录状态
 */
enum class IMLoginStatus(status: Int) {
    /**
     * 连接中
     */
    CONNECTING(0),

    /**
     * 连接成功
     */
    CONNECT_SUCCESS(1),

    /**
     * 连接失败
     */
    CONNECT_FAIL(-1),

    /**
     * 未启动
     */
    CONNECT_DEFAULT(2),
}
