package com.example.imclient.data.api

import com.example.imclient.ui.expression.entities.EmojiEntry
import kotlinx.coroutines.flow.Flow

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description:
 */
interface ExpressionService {
    suspend fun downloadEmojiDatabase(urlString: String): Flow<List<EmojiEntry>>

//    suspend fun getBigExpressionList(): Flow<List<Int>>
}