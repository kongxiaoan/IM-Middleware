package com.example.mylibrary.default

import com.example.mylibrary.factory.IMLongConnectionFactory

/**
 * Create by kpa(billkp@yeah.net) on 2023/3/13
 * 17:43
 * Describe ：默认长连接工厂
 */
class DefaultWebsocketFactory : IMLongConnectionFactory<DefaultLongConnectionImpl>() {

    companion object {
        @JvmStatic
        fun create(): DefaultWebsocketFactory {
            return DefaultWebsocketFactory()
        }
    }

    override fun createLongConnection(): DefaultLongConnectionImpl {
        return DefaultLongConnectionImpl()
    }
}