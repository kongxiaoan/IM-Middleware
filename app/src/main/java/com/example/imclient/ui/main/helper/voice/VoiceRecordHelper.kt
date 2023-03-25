package com.example.imclient.ui.main.helper.voice

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.im.middleware.R
import com.im.middleware.databinding.FragmentMainBinding
import com.example.imclient.utils.PermissionUtils
import com.example.imclient.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

/**
 *
 * @author: kpa
 * @date: 2023/3/24
 * @description: 自定义录音视图
 */
@SuppressLint("ClickableViewAccessibility")
class VoiceRecordHelper(
    val mActivity: FragmentActivity,
    val binding: FragmentMainBinding,
    val callback: (String?) -> Unit
) : LifecycleEventObserver {


    private var voiceCutDownTimer: CountDownTimer? = null

    private var voiceHasSend = false
    private var voiceRecordTime = 0L

    //录音临时地址
    private var tempVoicePath: String? = null

    //是否显示录音状态
    private var isShowVoiceRecording = false

    // 录音任务
    private var voiceRecordTimer: Timer? = null

    // 计算语音大小
    private var voiceSizeTask: TimerTask? = null


    init {
        binding.run {
            wrapperInputBox.imMiddlewareTv.run {
                setOnTouchListener { v, event ->
                    // 监测权限
                    if (!PermissionUtils.checkPermissions(mActivity)) {
                        return@setOnTouchListener false
                    }
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> { //显示松开结束
                            voiceHasSend = false
                            text = resources.getString(R.string.im_chat_unpress_send)
                            voiceCutDownTimer = object : CountDownTimer(60 * 1000L, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                }

                                override fun onFinish() {
                                    voiceHasSend = true
                                    // 结束录音
                                    recordDone(event)
                                }
                            }
                            voiceCutDownTimer?.start()
                            voiceRecordTime = System.currentTimeMillis()
                            //录音地址
                            tempVoicePath = ""
                            binding.viewSendVoiceStatus.isVisible = true
                            binding.ivRecordStatus.isVisible = true
                            //todo 调用开始录音功能

                            //用作UI展示
                            isShowVoiceRecording = true
                            voiceRecordTimer = Timer()
                            // 用于显示音量高低
                            voiceSizeTask = object : TimerTask() {
                                override fun run() {
                                    // 声音动画更新
                                    MainScope().launch(Dispatchers.Main) {
                                        if (isShowVoiceRecording) {
                                            // todo 获取真实声音大小
                                            val db = (0..100).random()
                                            when (db) {
                                                in 0..50 -> binding.ivRecordStatus.setImageResource(
                                                    R.drawable.message_record_0
                                                )
                                                in 50..55 -> binding.ivRecordStatus.setImageResource(
                                                    R.drawable.message_record_1
                                                )
                                                in 55..60 -> binding.ivRecordStatus.setImageResource(
                                                    R.drawable.message_record_1
                                                )
                                                in 60..65 -> binding.ivRecordStatus.setImageResource(
                                                    R.drawable.message_record_2
                                                )
                                                in 65..75 -> binding.ivRecordStatus.setImageResource(
                                                    R.drawable.message_record_3
                                                )
                                                in 75..80 -> binding.ivRecordStatus.setImageResource(
                                                    R.drawable.message_record_4
                                                )
                                                in 80..85 -> binding.ivRecordStatus.setImageResource(
                                                    R.drawable.message_record_5
                                                )
                                                else -> binding.ivRecordStatus.setImageResource(R.drawable.message_record_5)
                                            }
                                        } else {
                                            binding.ivRecordStatus.setImageResource(R.drawable.message_icon_withdraw)
                                        }
                                    }
                                }
                            }
                            // 100ms 一次
                            voiceRecordTimer?.schedule(voiceSizeTask, 100, 100)
                        }
                        MotionEvent.ACTION_MOVE -> {
                            if (event.rawY > Utils.getHeight(context)
                                    .times(RELEASE_CANCEL_OFFSET)
                            ) {
                                isShowVoiceRecording = true
                            } else {
                                isShowVoiceRecording = false
                                binding.viewSendVoiceStatus.text =
                                    resources.getString(R.string.im_chat_cancel_send)
                                binding.ivRecordStatus.setImageResource(R.drawable.message_icon_withdraw)
                            }
                        }
                        MotionEvent.ACTION_CANCEL -> {
                            text = resources.getString(R.string.im_chat_press_say)
                            if (!voiceHasSend) {
                                voiceCutDownTimer?.cancel()
                                recordDone(event)
                            }
                        }
                        MotionEvent.ACTION_UP -> {
                            text = resources.getString(R.string.im_chat_press_say)
                            if (!voiceHasSend) {
                                voiceCutDownTimer?.cancel()
                                recordDone(event)
                            }
                        }
                        else -> {
                            return@setOnTouchListener false
                        }
                    }
                    true
                }
            }
        }
    }

    companion object {
        private const val RELEASE_CANCEL_OFFSET = 0.75f
    }


    private fun recordDone(event: MotionEvent) {
        isShowVoiceRecording = false
        voiceRecordTimer?.cancel()
        binding.viewSendVoiceStatus.isVisible = false
        binding.ivRecordStatus.isVisible = false
        // 因该处理小于1S不能发送
        if (System.currentTimeMillis() - voiceRecordTime < 1 * 1000) {
            Toast.makeText(
                mActivity,
                mActivity.getString(R.string.im_chat_record_too_short),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        //判断区域
        if (event.rawY > (Utils.getHeight(mActivity).times(RELEASE_CANCEL_OFFSET) ?: 0f)) {
            //todo 停止录音
            callback.invoke(tempVoicePath)
        } else {
            //todo 取消录音
            val file = File(tempVoicePath)
            //查看录音文件是否存在 存在便删除
            if (file.exists()) {
                file.deleteOnExit()
            }
            tempVoicePath = ""
            callback.invoke(tempVoicePath)
        }
    }

    /**
     * Called when a state transition event happens.
     *
     * @param source The source of the event
     * @param event The event
     */
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            voiceCutDownTimer?.run {
                cancel()
                null
            }
            voiceRecordTimer?.run {
                cancel()
                null
            }
        }
    }
}