package com.example.imclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imclient.ui.main.IMMainFragment

class IMMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_im_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, IMMainFragment.newInstance())
                .commitNow()
        }
    }
}