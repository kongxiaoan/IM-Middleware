package com.example.imclient.appinitializer.impl

import com.example.imclient.appinitializer.AppInitializerStartType
import com.example.imclient.appinitializer.AppInitializers
import com.example.imclient.utils.Logger

/**
 *
 * @author: kpa
 * @date: 2023/4/13
 * @description:
 */
class Test1 : AppInitializers {
    override fun init() {
        Logger.log("Test1 初始化", "AppInitializersProvider")
    }

    override fun getStartType(): AppInitializerStartType {
        return AppInitializerStartType.TYPE_SERIES
    }

    override fun widget(): Int {
        return 2
    }
}

class Test2 : AppInitializers {
    override fun init() {
        Logger.log("Test2 初始化", "AppInitializersProvider")
    }

    override fun getStartType(): AppInitializerStartType {
        return AppInitializerStartType.TYPE_SERIES
    }

    override fun widget(): Int {
        return 9
    }
}

class Test3 : AppInitializers {
    override fun init() {
        Logger.log("Test3 初始化", "AppInitializersProvider")
    }

    override fun getStartType(): AppInitializerStartType {
        return AppInitializerStartType.TYPE_SERIES
    }

    override fun widget(): Int {
        return 8
    }
}

class Test4 : AppInitializers {
    override fun init() {
        Logger.log("Test4 初始化", "AppInitializersProvider")
    }

    override fun getStartType(): AppInitializerStartType {
        return AppInitializerStartType.TYPE_SERIES
    }

    override fun widget(): Int {
        return 7
    }
}

class Test5 : AppInitializers {
    override fun init() {
        Logger.log("Test5 初始化", "AppInitializersProvider")
    }

    override fun getStartType(): AppInitializerStartType {
        return AppInitializerStartType.TYPE_PARALLEL
    }

    override fun widget(): Int {
        return 10
    }
}

class Test6 : AppInitializers {
    override fun init() {
        Logger.log("Test6 初始化", "AppInitializersProvider")
    }

    override fun getStartType(): AppInitializerStartType {
        return AppInitializerStartType.TYPE_PARALLEL
    }

    override fun widget(): Int {
        return 10
    }
}

class Test7 : AppInitializers {
    override fun init() {
        Logger.log("Test7 初始化", "AppInitializersProvider")
    }

    override fun getStartType(): AppInitializerStartType {
        return AppInitializerStartType.TYPE_PARALLEL
    }

    override fun widget(): Int {
        return 10
    }
}
