package com.example.mylibrary.entities

/**
 *
 * @author: kpa
 * @date: 2023/3/7
 * @description: IM所需参数
 */
class IMParams constructor(builder: Builder) {
    var token: String = ""
    var ip = ""
    var port = 0
    var uid = ""

    init {
        token = builder.getToken()
        ip = builder.getIp()
        port = builder.getPort()
        uid = builder.getUid()
    }

    class Builder {
        private var token: String = ""
        private var ip = ""
        private var port = 0
        private var uid = ""

        fun withToken(token: String) = apply {
            this.token = token
        }

        fun getToken() = token

        fun withIp(ip: String) = apply {
            this.ip = ip
        }

        fun getIp() = ip

        fun withPort(port: Int) = apply {
            this.port = port
        }

        fun getPort() = port

        fun withUid(uid: String) = apply {
            this.uid = uid
        }

        fun getUid() = uid
        fun build(): IMParams {
            return IMParams(this)
        }

    }
}