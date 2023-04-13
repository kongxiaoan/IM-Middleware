package com.example.imclient.appinitializer.di

import com.example.imclient.appinitializer.AppInitializers
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 *
 * @author: kpa
 * @date: 2023/4/13
 * @description: 启动框架容器注解
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppInitializerEntryPoint {
    fun getAppInitializers(): Set<AppInitializers>
}
