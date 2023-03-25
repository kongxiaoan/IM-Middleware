package com.example.imclient

import android.app.Application
import android.content.Context
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description:
 */
class IMApplication: Application() {

    companion object {
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = this
        Glide.init(this, GlideBuilder())
        EmojiCompat.init(BundledEmojiCompatConfig(this))
    }
}