package com.example.imclient

import android.content.Context
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
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
        val fontRequest = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Montserrat Subrayada",
            R.array.com_google_android_gms_fonts_certs
        )
        val config = FontRequestEmojiCompatConfig(this, fontRequest)
        EmojiCompat.init(config)
    }
}