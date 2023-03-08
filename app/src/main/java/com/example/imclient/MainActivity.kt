package com.example.imclient

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.example.imclient.im.IMCore
import com.example.mylibrary.IMClient
import com.example.mylibrary.utils.Logger


class MainActivity : AppCompatActivity() {

    private val imLogTV: TextView by lazy {
        findViewById<TextView>(R.id.imLogTV)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Logger.logLiveData.observeForever {
            appendLog("$it")
        }
    }

    fun sendMessage(view: View) {
        val message = "你好 ${System.currentTimeMillis()} 我是客户端"
        IMCore.send(message)
        appendLog("$message")
    }


    /**
     * 追加日志信息到 TextView 中
     *
     * @param log 日志信息
     */
    private fun appendLog(log: String) {
        imLogTV.append(
            """
           $log
            
            """.trimIndent()
        )
        scrollToBottom()
    }

    private fun scrollToBottom() {
        val scrollView = findViewById<NestedScrollView>(R.id.scrollView)
        scrollView.post { scrollView.fullScroll(View.FOCUS_DOWN) }
    }

    fun clearMessage(view: View) {
        imLogTV.text = ""
    }

    fun connectSocket(view: View) {
        IMClient.connect()
    }

    fun disConnectSocket(view: View) {
        IMClient.disConnect()
    }


}