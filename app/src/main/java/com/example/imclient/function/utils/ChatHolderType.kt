package com.example.imclient.function.utils

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description: 消息Holder类型 用户 0~99为系统预留  100+为用户消息 偶数为接收消息 奇数为发送消息
 */
enum class ChatHolderType(val type: Int) {

    /**
     * 空消息
     */
    SYSTEM_EMPTY(0),

    /**
     * 系统消息 聊天日期 如 下午6：30
     */
    SYSTEM_TIME(20),

    /**
     * 接收 文本消息
     */
    RECEIVE_TEXT(100),

    /**
     * 发送 文本消息
     */
    SEND_TEXT(101),

    /**
     * 接收 图片消息
     */
    RECEIVE_IMG(102),

    /**
     * 发送 图片消息
     */
    SEND_IMG(103),

    /**
     * 接收 语音消息
     */
    RECEIVE_VOICE(104),

    /**
     * 发送 语音消息
     */
    SEND_VOICE(105),
}
