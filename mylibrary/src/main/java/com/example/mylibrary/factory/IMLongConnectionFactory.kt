package com.example.mylibrary.factory

import android.content.Context
import com.example.mylibrary.interfaces.LongConnectionService

/**
 * Create by kpa(billkp@yeah.net) on 2023/3/13
 * 17:27
 * Describe ：创建抽象工厂用于统一创建连接器
 *
 */
abstract class IMLongConnectionFactory<out T : LongConnectionService> {
    abstract fun createLongConnection(context: Context): T
}