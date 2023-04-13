package com.example.mylibrary.utils

import android.util.Base64
import com.google.protobuf.ByteString

/**
 * @author: kpa
 * @date: 2023/2/14
 * @description:
 */
object XConverters {
    fun byteStringToString(byteString: ByteString?): String? {
        return if (byteString != null) {
            Base64.encodeToString(byteString.toByteArray(), Base64.DEFAULT)
        } else {
            null
        }
    }

    fun stringToByteString(string: String?): ByteString? {
        return if (string != null) {
            ByteString.copyFrom(
                Base64.decode(
                    string,
                    Base64.DEFAULT,
                ),
            )
        } else {
            null
        }
    }
}
