package com.example.imclient.ui.main.entities

import com.example.imclient.function.utils.ChatHolderType
import com.example.imclient.function.utils.MsgFromType

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description: 聊天数据实体结构
 */
class ChatEntity {

    /**
     * 消息来源类型,区分与服务器交互类型/客户单本地用的构造类型
     */
    var msgFromType: MsgFromType = MsgFromType.MSG_SERVER

    var cmd: Int = 0

    var content: String? = null

    var userEntity: UserEntity? = null

    constructor(msgFromType: MsgFromType, cmd: Int, content: String?, userEntity: UserEntity) {
        this.msgFromType = msgFromType
        this.cmd = cmd
        this.content = content
        this.userEntity = userEntity
    }

    var chatType: ChatHolderType = ChatHolderType.SYSTEM_EMPTY
        get() {
            when (msgFromType) {
                MsgFromType.MSG_SERVER -> {
                    when (cmd) {
                        ChatHolderType.SEND_TEXT.type -> {
                            return ChatHolderType.SEND_TEXT
                        }
                        ChatHolderType.RECEIVE_TEXT.type -> {
                            return ChatHolderType.RECEIVE_TEXT
                        }
                        ChatHolderType.SEND_IMG.type -> {
                            return ChatHolderType.SEND_IMG
                        }
                        ChatHolderType.RECEIVE_IMG.type -> {
                            return ChatHolderType.RECEIVE_IMG
                        }
                        ChatHolderType.RECEIVE_VOICE.type -> {
                            return ChatHolderType.RECEIVE_VOICE
                        }
                        ChatHolderType.SEND_VOICE.type -> {
                            return ChatHolderType.SEND_VOICE
                        }
                    }
                }
                else -> {

                }
            }
            return ChatHolderType.SEND_TEXT
        }
}