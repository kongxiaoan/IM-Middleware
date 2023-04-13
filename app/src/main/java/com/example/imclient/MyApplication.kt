package com.example.imclient

import android.content.Context
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.example.imclient.appinitializer.AppInitializersProvider
import com.example.imclient.im.IMCore
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 *
 * @author: kpa
 * @date: 2023/2/22
 * @description:
 */
@HiltAndroidApp
class MyApplication : BaseApplication() {

    companion object {
        lateinit var context: Context
    }

    @Inject
    lateinit var mAppInitializer: AppInitializersProvider
    override fun initApp() {
        context = this
        mAppInitializer.startInit()
    }
}
