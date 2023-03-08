package com.example.imclient

import android.app.Application
import android.content.Context
import android.os.Process

/**
 *
 * @author: kpa
 * @date: 2023/2/22
 * @description:
 */
abstract class BaseApplication :Application(){

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        val myPid = Process.myPid()
        val mActivityManager =
            this.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        val var3 = mActivityManager.runningAppProcesses?.iterator()
        while (var3?.hasNext() == true) {
            val appProcessInfo = var3.next() as android.app.ActivityManager.RunningAppProcessInfo
            if (appProcessInfo.pid == myPid && appProcessInfo.processName.equals(
                    this.packageName,
                    ignoreCase = true
                )
            ) {
                this.initApp()
                break
            }
        }
    }

    abstract fun initApp()
}