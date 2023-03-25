package com.example.imclient.ui.main.helper

/**
 *
 * @author: kpa
 * @date: 2023/3/22
 * @description: 键盘状态
 */
enum class InputBoxState(state: Int) {

    VOICE_STATUE(0),

    EDIT_STATUE(1),

    EXPRESSION_STATUE(2),

    MORE_STATUE(3),

    /**
     * 初始状态
     */
    INIT_STATUE(4)
}