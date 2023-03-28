package com.example.imclient.ui.main

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imclient.base.OnRecyclerViewItemClickListener
import com.example.imclient.databinding.FragmentMainBinding
import com.example.imclient.function.utils.ChatHolderType
import com.example.imclient.function.utils.MsgFromType
import com.example.imclient.ui.main.adapter.IMMainAdapter
import com.example.imclient.ui.main.entities.ChatEntity
import com.example.imclient.ui.main.entities.UserEntity
import com.example.imclient.ui.main.helper.InputBoxCallback
import com.example.imclient.ui.main.helper.InputBoxState
import com.example.imclient.ui.main.helper.InputBoxViewHolder
import com.example.imclient.ui.main.helper.KeyBoardUILogic
import com.example.imclient.ui.main.helper.voice.VoiceRecordHelper
import com.example.imclient.utils.*
import java.util.*

class IMMainFragment : Fragment(), OnRecyclerViewItemClickListener<ChatEntity>, InputBoxCallback {

    private val mViewModel by activityViewModels<IMMainViewModel>()
    private var keyBoardUILogic: KeyBoardUILogic? = null
    private var mInputBoxViewHolder: InputBoxViewHolder? = null

    private val mAdapter: IMMainAdapter by lazy {
        IMMainAdapter().apply {
            registerOnItemClickListener(this@IMMainFragment)
        }
    }
    private val binding: FragmentMainBinding by lazy {
        FragmentMainBinding.inflate(LayoutInflater.from(context))
    }


    companion object {
        fun newInstance() = IMMainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initView()
        bindEvent()
        registerObserver()
        loadData()
        return binding.root
    }

    private fun loadData() {
        mViewModel.run {
            viewModel(this) {
                viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                    observe(getImChatEntities("uid"), ::renderChatEntities)
                }
            }
        }
    }

    private fun renderChatEntities(chatEntity: List<ChatEntity>?) {
        Logger.log("chatEntity = ${chatEntity?.size}")
        if (chatEntity?.isNotEmpty() == true) {
            mAdapter.addAll(chatEntity)
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun registerObserver() {
        binding.run {
            root.viewTreeObserver.addOnGlobalLayoutListener {
                val r = Rect()
                root.getWindowVisibleDisplayFrame(r)
                val screenHeight = root.rootView.height
                val keypadHeight = screenHeight - r.bottom
                //键盘是否弹出
                val diff = screenHeight * 0.15
                if (keypadHeight > diff) { // 15% of the screen height
                    imMiddlewareRV.scrollToPosition(mAdapter.getItemCount() - 1);
                }
            }

            imMiddlewareRV.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    mInputBoxViewHolder?.close()
                }
                false
            }
            mViewModel.run {
                inputBoxStateObserver.observe(this@IMMainFragment.viewLifecycleOwner) { status ->
                    Logger.log("IMMainFragment status = $status ")
                    when (status) {
                        InputBoxState.EDIT_STATUE -> {
                            // 打开软键盘
                            mInputBoxViewHolder?.clickChat()
                            // 表情恢复
                            changedExpressionClick(true)
                        }
                        InputBoxState.EXPRESSION_STATUE -> {
                            // 打开表情面板
                            mInputBoxViewHolder?.clickExpression()
                            // 语音恢复
                            changedVoiceAndEditClick(true)
                        }
                        InputBoxState.VOICE_STATUE -> {
                            // 打开软键盘
                            mInputBoxViewHolder?.close()
                            // 表情恢复
                            changedExpressionClick(true)
                            // 编辑器恢复
                            changedVoiceAndEditClick(false)
                        }
                        InputBoxState.MORE_STATUE -> {
                            // 打开软键盘
                            mInputBoxViewHolder?.clickMore()
                            Logger.log("inputBoxStateObserver voice = ${voiceClickEnabled.value} express = ${expressionClick.value}")
                            // 表情恢复
                            changedVoiceAndEditClick(true)
                            // 编辑器恢复
                            changedExpressionClick(true)
                        }
                        else -> {
                            // 表情恢复
                            changedExpressionClick(true)
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun bindEvent() {
        binding.wrapperInputBox.run {
            viewModel = mViewModel
            lifecycleOwner = this@IMMainFragment
        }
        binding.hideHelperView.setOnClickListener {
            mInputBoxViewHolder?.close()
        }
        mInputBoxViewHolder?.setStateListener {
            Logger.log("键盘类型 ---> $it")
        }
        keyBoardUILogic?.registerKeyBoardListener()
        VoiceRecordHelper(requireActivity(), binding) { voiceRecordPath ->

        }
    }

    private fun initView() {
        binding.wrapperInputBox.imMiddlewareET.run {
            imeOptions = EditorInfo.IME_ACTION_SEND
            setHorizontallyScrolling(false)
            maxLines = Int.MAX_VALUE
        }
        mInputBoxViewHolder =
            InputBoxViewHolder(requireActivity(), binding, mViewModel, this).apply {
                keyBoardUILogic = KeyBoardUILogic(
                    this@IMMainFragment,
                    this,
                    binding
                )
            }
        binding.imMiddlewareRV.run {
            layoutManager =
                LinearLayoutManager(
                    this@IMMainFragment.requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = mAdapter
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermissionUtils.RECORD_AUDIO_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 录音权限已授予
                } else {
                    // 录音权限被拒绝，可以在这里提示用户开启权限
                }
                return
            }
            PermissionUtils.READ_EXTERNAL_STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 读取外部存储权限已授予
                } else {
                    // 读取外部存储权限被拒绝，可以在这里提示用户开启权限
                }
                return
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        keyBoardUILogic?.unRegisterKeyBoardListener()
        mInputBoxViewHolder?.removeStateListener()
    }

    override fun onItemClick(v: View, t: ChatEntity, position: Int) {

    }

    override fun editText(content: CharSequence?) {

    }

    override fun send(content: CharSequence?) {
        if(content.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "发送内容不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        Logger.log("发送的内容 ${content.toString()}")
        val userEntity = UserEntity()
        userEntity.gender = 1
        userEntity.avator =
            "https://gw.alicdn.com/i2/63986519/O1CN01AH1zKP1y1kkNYZE61_!!63986519.jpg_300x300Q75.jpg_.webp"
        mAdapter.append(ChatEntity(
            MsgFromType.MSG_SERVER,
            ChatHolderType.SEND_TEXT.type,
            content.toString(),
            userEntity
        ))
        binding.imMiddlewareRV.scrollToPosition(mAdapter.itemCount - 1);
    }
}
