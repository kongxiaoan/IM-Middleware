package com.example.mylibrary.factory

import com.example.mylibrary.listener.ILongConnectionService

/**
 * Create by kpa(billkp@yeah.net) on 2023/3/13
 * 17:27
 * Describe ：创建抽象工厂用于统一创建连接器
 *
 */
abstract class IMLongConnectionFactory<out T : ILongConnectionService.Stub> {
    abstract fun createLongConnection(): T
}
