package com.example.imclient.ui.main.helper

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.imclient.R
import com.example.imclient.databinding.FragmentMainBinding
import com.example.imclient.ui.box_more.InputBoxMoreFragment
import com.example.imclient.ui.expression.ExpressionContainerFragment
import com.example.imclient.ui.main.IMMainViewModel
import com.example.imclient.utils.Logger
import com.example.imclient.utils.dpToPx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class InputBoxViewHolder(
    val mActivity: FragmentActivity,
    val binding: FragmentMainBinding,
    val viewModel: IMMainViewModel
) :
    View.OnClickListener {

    private val wrapperRoot = binding.wrapperInputBox

    private var keybordClose = false

    /**
     * 软键盘高度
     */
    private var mKeyboardHeight: Int = 0
        get() {
            if (field == 0) {
                field = 981
            }
            return field
        }

    private var stateListener: StateListener? = null

    private var fragmentList = arrayListOf(
        ExpressionContainerFragment.newInstance(this)
    )

    private var moreFragmentList = arrayListOf<Fragment>(
        InputBoxMoreFragment.newInstance()
    )

    fun editText(content: String) {
        binding.wrapperInputBox.imMiddlewareET.run {
            text = this.text.append(content)
        }
    }

    private val inputBoxViewAdapter by lazy {
        Logger.log("fragmentList size" + fragmentList.size)
        object : FragmentStateAdapter(mActivity) {
            /**
             * Returns the total number of items in the data set held by the adapter.
             *
             * @return The total number of items in this adapter.
             */
            /**
             * Returns the total number of items in the data set held by the adapter.
             *
             * @return The total number of items in this adapter.
             */
            override fun getItemCount(): Int {
                return fragmentList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }
        }
    }


    private val inputBoxViewMoreAdapter by lazy {
        object : FragmentStateAdapter(mActivity) {
            /**
             * Returns the total number of items in the data set held by the adapter.
             *
             * @return The total number of items in this adapter.
             */
            /**
             * Returns the total number of items in the data set held by the adapter.
             *
             * @return The total number of items in this adapter.
             */
            override fun getItemCount(): Int {
                return moreFragmentList.size
            }

            override fun createFragment(position: Int): Fragment {
                return moreFragmentList[position]
            }
        }
    }


    /**
     * 软键盘-emoji 状态
     */
    private var mState = STATE_NONE
        set(value) {
            Logger.log("当前键盘类型 = $value ${stateListener == null}")
            stateListener?.stateCallback(value)
            field = value
        }

    fun interface StateListener {
        fun stateCallback(state: Int)
    }

    fun setStateListener(stateListener: StateListener) {
        Logger.log("设置键盘类型监听")
        this.stateListener = stateListener
    }

    fun removeStateListener() {
        this.stateListener = null
    }


    companion object {
        const val STATE_NONE = 0
        const val STATE_KEYBOARD = 1
        const val STATE_EMOJI = 2
        const val STATE_MORE = 3
    }


    init {
        wrapperRoot?.clickListener = this
        initInputSendView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            //点击弹幕卡

        }
    }

    /**
     * 初始化文字输入相关
     */
    private fun initInputSendView() {
        wrapperRoot.imMiddlewareET.apply {
            //限制输入数量
            filters = arrayOf<InputFilter>(InputFilter.LengthFilter(140))
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
            //监听软键盘上的发送按钮
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                }
                true
            }
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    Logger.log("inputBoxStateObserver hasFocus ")
                    viewModel.changedInputBoxStatus(InputBoxState.EDIT_STATUE)
                }
            }
        }
        // 表情rv初始化
        mActivity.supportFragmentManager.beginTransaction()
            .add(R.id.imMiddlewareVP, ExpressionContainerFragment.newInstance(this))
            .commit()

        // More初始化
        wrapperRoot.imMiddlewareMoreVP.apply {
            adapter = inputBoxViewMoreAdapter
        }
    }


    fun clickChat() {
        popSendArea(STATE_KEYBOARD)
    }

    fun clickExpression() {
        popSendArea(STATE_EMOJI)
    }

    fun clickMore() {
        popSendArea(STATE_MORE)
    }

    private fun popSendArea(initState: Int = STATE_EMOJI) {
        wrapperRoot.imMiddlewareET.run {
            isFocusableInTouchMode = true
            isFocusable = true
        }
        wrapperRoot.imMiddlewareET.post {
            updateState(initState)
        }
    }

    private fun updateState(newState: Int) {
        Log.e("KeyBoardUILogic", "updateState curState:$mState newState:$newState")
        if (mState == newState) return
        mState = newState
        when (mState) {
            STATE_NONE -> {// 什么都不显示
                wrapperRoot.imMiddlewareET.clearFocus()
                if (wrapperRoot.imMiddlewareVP.isVisible) {
                    wrapperRoot.imMiddlewareVP.isVisible = false
                }
                if (wrapperRoot.imMiddlewareMoreVP.isVisible) {
                    wrapperRoot.imMiddlewareMoreVP.isVisible = false
                }
                wrapperRoot.imMiddlewareET.run {
                    hideSoftKeyBoard(this) {

                    }
                }
                viewModel.changedInputBoxStatus(InputBoxState.INIT_STATUE)
            }
            STATE_KEYBOARD -> {// 显示键盘
                showKeyboardArea()
            }
            STATE_EMOJI -> {// 显示表情
                showEmojiArea()
            }
            STATE_MORE -> { //显示更多
                showMoreArea()
            }
            else -> {
                showEmojiArea()
            }
        }
    }


    private fun showMoreArea() {
        wrapperRoot.imMiddlewareET.clearFocus()
        //3. 关闭键盘
        wrapperRoot.imMiddlewareET.run {
            if (keybordClose) {
                updateMoreView()
            } else {
                hideSoftKeyBoard(this) {
                    updateMoreView()
                }
            }
        }
    }

    private fun updateMoreView() {
        MainScope().launch(Dispatchers.Main) {
            wrapperRoot.imMiddlewareVP.run {
                this.visibility = View.GONE
            }
            // 2. 设置表情区域高度
            wrapperRoot.imMiddlewareMoreVP.run {
                Logger.log("mKeyboardHeight = ${mKeyboardHeight}")
                val lp: ViewGroup.LayoutParams = this.layoutParams
                lp.height = 200.dpToPx()
                this.layoutParams = lp
                this.visibility = View.VISIBLE
            }
        }
    }


    private fun showKeyboardArea() {
        Logger.log("showKeyboardArea --- > showKeyboardArea")
        wrapperRoot.imMiddlewareET.requestFocus()
        Handler(Looper.myLooper()!!).postDelayed({
            wrapperRoot.imMiddlewareVP.run {
                this.visibility = View.GONE
            }
            // 2. 设置表情区域高度
            wrapperRoot.imMiddlewareMoreVP.run {
                this.visibility = View.GONE
            }
        }, 0)
        wrapperRoot.imMiddlewareET.run {
            showSoftKeyBoard(this)
        }
    }

    private fun showEmojiArea() {
        Logger.log("mKeyboardHeight = showEmojiArea")
        // 1. 清除EditText 焦点
        wrapperRoot.imMiddlewareET.clearFocus()
        //3. 关闭键盘
        wrapperRoot.imMiddlewareET.run {
            if (keybordClose) {
                updateEmojiView()
            } else {
                hideSoftKeyBoard(this) {
                    updateEmojiView()
                }
            }
        }
    }

    private fun updateEmojiView() {
        // 2. 设置表情区域高度
        wrapperRoot.imMiddlewareMoreVP.run {
            this.visibility = View.GONE
        }
        // 2. 设置表情区域高度
        wrapperRoot.imMiddlewareVP.run {
            Logger.log("mKeyboardHeight = ${mKeyboardHeight}")
            val lp: ViewGroup.LayoutParams = this.layoutParams
            lp.height = mKeyboardHeight
            this.layoutParams = lp
            this.visibility = View.VISIBLE
        }
        Logger.log("imMiddlewareVP = ${binding.wrapperInputBox.imMiddlewareVP.visibility}")
    }

    fun close() {
        updateState(STATE_NONE)
    }

    /**
     * 软键盘已经关闭事件,用于在软键盘关闭的事件中调用。
     */
    fun softKeyBoardCloseEvent() {
        when (mState) {
            STATE_KEYBOARD -> {
                // 如果当前状态是显示键盘状态，但是软键盘缺关闭了，说明可能是受外力关闭的，并不是通过切换。
                // 比如点击软键盘上的关闭按钮、点击键盘区域外的事件手动关闭键盘。
                close()
            }
        }
    }

    fun setKeyBoardHeight(height: Int) {
        this.mKeyboardHeight = height
    }

    private fun showSoftKeyBoard(view: View) {
        val imm = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        val resultReceiver = object : ResultReceiver(Handler(Looper.getMainLooper())) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                super.onReceiveResult(resultCode, resultData)
                // 在这里处理软键盘打开后的逻辑
                Logger.log("showKeyboardArea 打开了软键盘")
                keybordClose = false
            }
        }
        imm?.showSoftInput(view, InputMethodManager.SHOW_FORCED, resultReceiver)
    }

    private fun hideSoftKeyBoard(view: View, callback: () -> Unit) {
        val imm = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (imm != null && imm.isActive) {
            val resultReceiver = object : ResultReceiver(Handler(Looper.getMainLooper())) {
                override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                    super.onReceiveResult(resultCode, resultData)
                    // 在这里处理软键盘隐藏完成后的逻辑
                    callback.invoke()
                    keybordClose = true
                }
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0, resultReceiver)
        }
    }

}