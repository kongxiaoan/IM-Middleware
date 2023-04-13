package com.example.imclient.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

/**
 *
 * @author: kpa
 * @date: 2023/3/24
 * @description: 权限管理
 */
object PermissionUtils {

    const val RECORD_AUDIO_PERMISSION_CODE = 100
    const val READ_EXTERNAL_STORAGE_PERMISSION_CODE = 101

    /**
     * 检查并在没有权限时申请权限
     */
    fun checkPermissions(mActivity: FragmentActivity): Boolean {
        var hasPermission = true
        // 检查录音权限
        if (ContextCompat.checkSelfPermission(
                mActivity,
                Manifest.permission.RECORD_AUDIO,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            hasPermission = false
            // 如果没有录音权限，则请求录音权限
            ActivityCompat.requestPermissions(
                mActivity,
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                ),
                RECORD_AUDIO_PERMISSION_CODE,
            )
        }

//        // 检查读取外部存储权限
//        if (ContextCompat.checkSelfPermission(
//                mActivity,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            hasPermission = false
//            // 如果没有读取外部存储权限，则请求读取外部存储权限
//            ActivityCompat.requestPermissions(
//                mActivity,
//                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                READ_EXTERNAL_STORAGE_PERMISSION_CODE
//            )
//        }
        return hasPermission
    }
}
