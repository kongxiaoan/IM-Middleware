package com.example.imclient.data.api

import com.example.imclient.ui.main.entities.ChatEntity
import kotlinx.coroutines.flow.Flow

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description: 统一数据源接口
 */
interface IMMainService {

    suspend fun getImChatEntities(chatWithId: String): Flow<List<ChatEntity>>
}
