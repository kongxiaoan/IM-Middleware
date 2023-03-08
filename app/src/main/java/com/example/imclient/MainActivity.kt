package com.example.imclient

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.imclient.im.IMCore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun sendMessage(view: View) {
        IMCore.send("你好 ${System.currentTimeMillis()}")
    }


}