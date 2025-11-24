package com.enet.sinar.ui.view.general.pay

import android.content.Context
import android.content.Intent

fun shareText(context: Context, text: String, packageName: String? = null) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
        packageName?.let { setPackage(it) }
    }
    context.startActivity(Intent.createChooser(intent, "اشتراک‌گذاری با"))
}
