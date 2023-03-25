package com.example.imclient

import android.content.Context
import androidx.core.provider.FontRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.example.imclient.im.IMCore

/**
 *
 * @author: kpa
 * @date: 2023/2/22
 * @description:
 */
class MyApplication : BaseApplication() {

    companion object {
        lateinit var context: Context
    }
    override fun initApp() {
        context = this
        IMCore.init(this)
        Glide.init(this, GlideBuilder())
//        val fontRequest = FontRequest(
//            "com.example.fontprovider",
//            "com.example",
//            "emoji compat Font Query",
//            CERTIFICATES
//        )
//        val config = FontRequestEmojiCompatConfig(this, fontRequest)
//        EmojiCompat.init(FontRequestEmojiCompatConfig.)
    }
}