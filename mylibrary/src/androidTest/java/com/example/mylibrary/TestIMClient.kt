package com.example.mylibrary

import androidx.test.platform.app.InstrumentationRegistry
import com.example.mylibrary.default.DefaultWebsocketFactory

/**
 * Create by kpa(billkp@yeah.net) on 2023/3/13
 * 17:57
 * Describe ：注释说明信息
 */
class TestIMClient {

    fun test() {

        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        // 初始化
        IMClient.Builder()
            .withFactory(DefaultWebsocketFactory.create()).build()

        // 使用
        IMClient.with().connect()
        IMClient.with().send("我是发送的消息内容")

    }
}