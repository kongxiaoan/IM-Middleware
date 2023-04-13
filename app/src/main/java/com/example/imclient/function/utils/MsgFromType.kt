package com.example.imclient.function.utils

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description: 消息类型 ，区分服务器还是本地数据
 */
enum class MsgFromType(val msgFromType: Int) {
    // 消息展示类型-与服务端交互的消息
    MSG_SERVER(0),

    // 消息展示类型-本地时间戳
    MSG_TIME(1),
}
