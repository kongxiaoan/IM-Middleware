package com.example.imclient.data

import androidx.lifecycle.LiveData
import com.example.imclient.data.api.IMMainService
import com.example.imclient.function.utils.ChatHolderType
import com.example.imclient.function.utils.MsgFromType
import com.example.imclient.ui.main.entities.ChatEntity
import com.example.imclient.ui.main.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description: 数据仓库
 */
class IMMainRepository {
    private val service: IMMainService by lazy {
        // 后续进行分离
        object : IMMainService {
            override suspend fun getImChatEntities(chatWithId: String): Flow<List<ChatEntity>> {
                val testData = arrayListOf<ChatEntity>()
                // todo 测试数据
                return flow {
                    for (i in 1..100) {
                        if (i < 10) {
                            val userEntity = UserEntity()
                            userEntity.gender = 2
                            userEntity.avator =
                                "https://www.mn52.com/tuku/9fe1bcd0e7b204d446408e06de0b4dbb.jpg"
                            testData.add(
                                ChatEntity(
                                    MsgFromType.MSG_SERVER,
                                    ChatHolderType.SEND_IMG.type,
                                    "https://img.win3000.com/m00/d7/79/d9859520938232779b5dbc4cd0d1283c_c_224_336.jpg",
                                    userEntity
                                )
                            )
                            continue
                        } else if (i < 20) {
                            val userEntity = UserEntity()
                            userEntity.gender = 2
                            userEntity.avator =
                                "https://www.mn52.com/tuku/9fe1bcd0e7b204d446408e06de0b4dbb.jpg"
                            testData.add(
                                ChatEntity(
                                    MsgFromType.MSG_SERVER,
                                    ChatHolderType.RECEIVE_TEXT.type,
                                    "我是测试数据$i",
                                    userEntity
                                )
                            )
                            continue
                        } else if (i < 30) {
                            val userEntity = UserEntity()
                            userEntity.gender = 1
                            userEntity.avator =
                                "https://gw.alicdn.com/i2/63986519/O1CN01AH1zKP1y1kkNYZE61_!!63986519.jpg_300x300Q75.jpg_.webp"
                            testData.add(
                                ChatEntity(
                                    MsgFromType.MSG_SERVER,
                                    ChatHolderType.SEND_TEXT.type,
                                    "我是测试数据$i",
                                    userEntity
                                )
                            )
                            continue
                        } else if (i < 40) {
                            val userEntity = UserEntity()
                            userEntity.gender = 1
                            userEntity.avator =
                                "https://gw.alicdn.com/i2/63986519/O1CN01AH1zKP1y1kkNYZE61_!!63986519.jpg_300x300Q75.jpg_.webp"
                            testData.add(
                                ChatEntity(
                                    MsgFromType.MSG_SERVER,
                                    ChatHolderType.RECEIVE_IMG.type,
                                    "https://img.win3000.com/m00/26/e1/9d51bbd60614390b4ff94f38138eb71f.jpg",
                                    userEntity
                                )
                            )
                            continue
                        } else if(i < 50) {
                            val userEntity = UserEntity()
                            userEntity.gender = 1
                            userEntity.avator =
                                "https://gw.alicdn.com/i2/63986519/O1CN01AH1zKP1y1kkNYZE61_!!63986519.jpg_300x300Q75.jpg_.webp"
                            testData.add(
                                ChatEntity(
                                    MsgFromType.MSG_SERVER,
                                    ChatHolderType.SEND_VOICE.type,
                                    "https://img.win3000.com/m00/26/e1/9d51bbd60614390b4ff94f38138eb71f.jpg",
                                    userEntity
                                )
                            )
                            continue
                        } else if(i < 60) {
                            val userEntity = UserEntity()
                            userEntity.gender = 1
                            userEntity.avator =
                                "https://gw.alicdn.com/i2/63986519/O1CN01AH1zKP1y1kkNYZE61_!!63986519.jpg_300x300Q75.jpg_.webp"
                            testData.add(
                                ChatEntity(
                                    MsgFromType.MSG_SERVER,
                                    ChatHolderType.RECEIVE_VOICE.type,
                                    "https://img.win3000.com/m00/26/e1/9d51bbd60614390b4ff94f38138eb71f.jpg",
                                    userEntity
                                )
                            )
                            continue
                        }
                    }
                    emit(testData)
                }
            }

        }
    }

    suspend fun getImChatEntities(chatWithId: String): Flow<List<ChatEntity>> {
        return service.getImChatEntities(chatWithId)
    }
}