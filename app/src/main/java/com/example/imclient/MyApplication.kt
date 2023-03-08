package com.example.imclient

import androidx.core.content.ContextCompat
import com.example.imclient.im.IMCore

/**
 *
 * @author: kpa
 * @date: 2023/2/22
 * @description:
 */
class MyApplication : BaseApplication() {
    override fun initApp() {
        IMCore.init(this)
    }
}