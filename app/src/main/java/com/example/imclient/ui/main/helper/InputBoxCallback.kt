package com.example.imclient.ui.main.helper

/**
 *
 * @author: kpa
 * @date: 2023/3/24
 * @description:
 */
interface InputBoxCallback {
    fun editText(content: CharSequence?)

    fun send(content: CharSequence?)
}