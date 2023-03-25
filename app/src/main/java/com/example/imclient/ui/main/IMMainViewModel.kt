package com.example.imclient.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imclient.data.IMMainRepository
import com.example.imclient.ui.main.entities.ChatEntity
import com.example.imclient.utils.Logger
import com.example.imclient.ui.main.helper.InputBoxState
import kotlinx.coroutines.flow.Flow

class IMMainViewModel : ViewModel() {
    private val _voiceClick = MutableLiveData(true)

    private val _expressionClick = MutableLiveData(true)

    private val _addClick = MutableLiveData(false)

    private val _inputBoxStatus = MutableLiveData(InputBoxState.EDIT_STATUE)


    val inputBoxStateObserver: LiveData<InputBoxState>
        get() = _inputBoxStatus

    val voiceClickEnabled: LiveData<Boolean>
        get() = _voiceClick

    val expressionClick: LiveData<Boolean>
        get() = _expressionClick

    val addMoreClick: LiveData<Boolean>
        get() = _addClick


    /**
     * 获取会话数据
     */
    suspend fun getImChatEntities(chatWithId: String): Flow<List<ChatEntity>> {
        return IMMainRepository().getImChatEntities(chatWithId)
    }

    fun changedInputBoxStatus(status: InputBoxState) {
        _inputBoxStatus.value = status
    }

    fun changedExpressionClick(status: Boolean) {
        if (_expressionClick.value != status) {
            _expressionClick.value = status
        }
    }

    fun changedVoiceAndEditClick(status: Boolean) {
        if (_voiceClick.value != status) {
            _voiceClick.value = status
        }
    }

    fun onVoiceAndKeyboardClick(view: View) {
        var b = _voiceClick.value ?: false
        view.run {
            isSelected = !b
            _voiceClick.value = isSelected
            changedInputBoxStatus(if (isSelected) InputBoxState.EDIT_STATUE else InputBoxState.VOICE_STATUE)
        }
        Logger.log("inputBoxStateObserver onVoiceAndKeyboardClick ${_voiceClick.value}")
    }

    fun onExpressionAndKeyboardClick(view: View) {
        val b = _expressionClick.value ?: false
        view.run {
            isSelected = !b
            _expressionClick.value = isSelected
            Logger.log("inputBoxStateObserver onExpressionAndKeyboardClick ${_expressionClick.value}")
            changedInputBoxStatus(if (isSelected) InputBoxState.EDIT_STATUE else InputBoxState.EXPRESSION_STATUE)
        }
    }

    fun onAddMoreClick(view: View) {
        _addClick.value = !(_addClick.value ?: false)
        changedInputBoxStatus(if (_addClick.value == true) InputBoxState.MORE_STATUE else InputBoxState.EDIT_STATUE)
    }
}