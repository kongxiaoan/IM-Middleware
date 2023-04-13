package com.example.imclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
