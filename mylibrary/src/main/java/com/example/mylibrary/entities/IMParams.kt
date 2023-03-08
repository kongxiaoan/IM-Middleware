package com.example.mylibrary.entities

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * @author: kpa
 * @date: 2023/3/7
 * @description: IM所需参数
 */
class IMParams : Parcelable {
    var token: String = ""
    var ip = ""
    var port = 0
    var uid = ""

    constructor(parcel: Parcel) {
        token = parcel.readString().toString()
        ip = parcel.readString().toString()
        port = parcel.readInt()
        uid = parcel.readString().toString()
    }

    constructor(builder: Builder) {
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(token)
        parcel.writeString(ip)
        parcel.writeInt(port)
        parcel.writeString(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IMParams> {
        override fun createFromParcel(parcel: Parcel): IMParams {
            return IMParams(parcel)
        }

        override fun newArray(size: Int): Array<IMParams?> {
            return arrayOfNulls(size)
        }
    }
}