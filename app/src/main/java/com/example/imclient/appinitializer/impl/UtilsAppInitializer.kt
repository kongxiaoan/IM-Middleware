package com.example.imclient.appinitializer.impl

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.example.imclient.appinitializer.AppInitializerStartType
import com.example.imclient.appinitializer.AppInitializers
import com.example.imclient.im.IMCore

/**
 *
 * @author: kpa
 * @date: 2023/4/13
 * @description: 工具类初始化
 */
class UtilsAppInitializer(val application: Application) : AppInitializers {
    override fun init() {
        IMCore.init(application)
        Glide.init(application, GlideBuilder())
    }

    override fun getStartType(): AppInitializerStartType {
        return AppInitializerStartType.TYPE_SERIES
    }

    override fun widget(): Int {
        return 11
    }
}
