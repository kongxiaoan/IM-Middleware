package com.example.imclient.appinitializer

/**
 *
 * @author: kpa
 * @date: 2023/4/13
 * @description: 启动类型
 */
enum class AppInitializerStartType {
    /**
     * 串行执行
     */
    TYPE_SERIES,

    /**
     * 并发执行
     */
    TYPE_PARALLEL,
}