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
    var url = ""
    var uid = ""


    constructor(parcel: Parcel) {
        token = parcel.readString().toString()
        url = parcel.readString().toString()
        uid = parcel.readString().toString()
    }

    constructor(builder: Builder) {
        token = builder.getToken()
        url = builder.getUrl()
        uid = builder.getUid()
    }

    class Builder {
        private var token: String = ""
        private var url = ""
        private var uid = ""

        fun withToken(token: String) = apply {
            this.token = token
        }

        fun getToken() = token

        fun withUrl(url: String) = apply {
            this.url = url
        }

        fun getUrl() = url


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
        parcel.writeString(url)
        parcel.writeString(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "IMParams(token='$token', url='$url', uid='$uid')"
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