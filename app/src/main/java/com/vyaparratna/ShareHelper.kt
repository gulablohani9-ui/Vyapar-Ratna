package com.vyaparratna

import android.content.Context
import android.content.Intent

object ShareHelper {
    fun shareText(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "व्यापार रत्न - पूर्वानुमान")
            putExtra(Intent.EXTRA_TEXT, text)
        }
        val chooser = Intent.createChooser(intent, "शेयर करें")
        context.startActivity(chooser)
    }
}
