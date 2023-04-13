package com.example.imclient.appinitializer.di

import android.app.Application
import com.example.imclient.appinitializer.AppInitializers
import com.example.imclient.appinitializer.impl.* // ktlint-disable no-wildcard-imports
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 *
 * @author: kpa
 * @date: 2023/4/13
 * @description: 添加容器注入
 */
@Module
@InstallIn(SingletonComponent::class)
object AppInitializersModule {
//    todo Test 代码
//    @Provides
//    fun provideAppInitializers(application: Application): Set<AppInitializers> = setOf(
//        Test1(),
//        Test2(),
//        Test3(),
//        Test4(),
//        Test5(),
//        Test6(),
//        Test7(),
//    )

    @Provides
    fun provideAppInitializers(application: Application): Set<AppInitializers> = setOf(
        EmojiInitializer(application),
        UtilsAppInitializer(application),
    )
}
