package com.example.imclient.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

/**
 *
 * @author: kpa
 * @date: 2023/3/23
 * @description:
 */
class EmojiTextView : androidx.appcompat.widget.AppCompatTextView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr,
    ) {
        // 初始化时将Emoji字体设置到TextView上
        val emojiTypeface =
            Typeface.createFromAsset(context.assets, "fonts/Montserrat-Regular.otf")
        typeface = emojiTypeface
    }
}
