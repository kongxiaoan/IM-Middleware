package com.example.imclient.ui.expression

import androidx.lifecycle.ViewModel
import com.example.imclient.data.ExpressionRepository
import com.example.imclient.ui.expression.entities.EmojiEntry
import kotlinx.coroutines.flow.Flow

class ExpressionViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var emojiDatabaseUrl = "https://unicode.org/Public/emoji/14.0/emoji-test.txt"


    suspend fun getEmojiList() :Flow<List<EmojiEntry>>{
        return ExpressionRepository().getEmojiList(emojiDatabaseUrl)
    }

}