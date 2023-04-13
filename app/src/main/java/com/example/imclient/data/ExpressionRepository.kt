package com.example.imclient.data

import android.content.Context
import com.example.imclient.MyApplication
import com.example.imclient.data.api.ExpressionService
import com.example.imclient.ui.expression.entities.EmojiEntry
import com.example.imclient.utils.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description:
 */
class ExpressionRepository {

    private val service: ExpressionService by lazy {
        // 后续进行分离
        object : ExpressionService {
            override suspend fun downloadEmojiDatabase(urlString: String): Flow<List<EmojiEntry>> {
                Logger.log("当前线程 ${Thread.currentThread().name}")
                return flow {
//                    val pattern = Regex("^(\\S+)\\s+;\\s+fully-qualified\\s+#\\s+(\\S+)\\s+(.+)$")
                    val pattern =
                        Regex("^(\\S+)\\s+;\\s+fully-qualified\\s+#\\s+((?:\\S+\\s+)+)(.+)$")
                    val filterNotNull = readAssetsFile("emoji.txt", MyApplication.context)
                        .trim()
                        .lines()
                        .map { line ->
                            val matchResult = pattern.find(line)
                            if (matchResult != null) {
                                val (emoji, codePointHex, comment) = matchResult.destructured
                                val codePoint = emoji.drop(2).toInt(16)
                                EmojiEntry(
                                    emoji,
                                    codePoint,
                                    "E${emoji.take(2)}",
                                    comment,
                                    codePointHex,
                                )
                            } else {
                                null
                            }
                        }.filterNotNull()

                    emit(filterNotNull)
                }
            }
        }
    }

    private fun readAssetsFile(fileName: String, context: Context): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    suspend fun getEmojiList(url: String): Flow<List<EmojiEntry>> {
        return service.downloadEmojiDatabase(url)
    }
}
