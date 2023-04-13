package com.example.imclient.appinitializer.impl

import android.app.Application
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.example.imclient.R
import com.example.imclient.appinitializer.AppInitializerStartType
import com.example.imclient.appinitializer.AppInitializers

/**
 *
 * @author: kpa
 * @date: 2023/4/13
 * @description: 表情初始化器
 */
class EmojiInitializer(val application: Application) : AppInitializers {
    override fun init() {
        val fontRequest = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Montserrat Subrayada",
            R.array.com_google_android_gms_fonts_certs,
        )
        val config = FontRequestEmojiCompatConfig(application, fontRequest)
        EmojiCompat.init(config)
    }

    override fun getStartType(): AppInitializerStartType {
        return AppInitializerStartType.TYPE_SERIES
    }

    override fun widget(): Int {
        // 可维护一个优先级序列
        return 10
    }
}
