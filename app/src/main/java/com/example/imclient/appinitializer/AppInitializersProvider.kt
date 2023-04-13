package com.example.imclient.appinitializer

import android.app.Application
import com.example.imclient.appinitializer.di.AppInitializerEntryPoint
import com.example.imclient.utils.Logger
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 *
 * @author: kpa
 * @date: 2023/4/13
 * @description: 启动 框架提供者 入口
 */
class AppInitializersProvider @Inject constructor(application: Application) {
    private val initializers: Set<AppInitializers> by lazy {
        EntryPointAccessors.fromApplication(application, AppInitializerEntryPoint::class.java)
            .getAppInitializers()
    }

    fun startInit() {
        var seriesList = initializers.filter {
            it.getStartType() == AppInitializerStartType.TYPE_SERIES
        }.sortedByDescending { it.widget() }
        var parallelList =
            initializers.filter { it.getStartType() == AppInitializerStartType.TYPE_PARALLEL }
        Logger.log("开始执行 并行", "AppInitializersProvider")
        parallelList.parallelStream().forEach {
            MainScope().launch(Dispatchers.IO) {
                it.init()
            }
        }
        Logger.log("结束执行 并行", "AppInitializersProvider")
        seriesList.forEach {
            it.init()
        }
    }
}
