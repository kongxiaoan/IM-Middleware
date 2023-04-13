package com.example.imclient.ui.expression.entities

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description:
 */
data class EmojiEntry(
    val emoji: String,
    val codePoint: Int,
    val version: String,
    val comment: String,
    val codePointHex: String,
)
