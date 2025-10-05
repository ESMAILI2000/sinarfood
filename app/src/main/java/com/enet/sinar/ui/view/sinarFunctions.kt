package com.enet.sinar.ui.view

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

data class MenuItem(val text:String,val icon:Painter)

data class BottomBarItem(val text:String,val icon:Painter,val selectedIcon:Painter)

data class TextFeildItem(
    val holder:String,
    val icon:Painter,
    var value: MutableState<String>,
    val isNumeric: Boolean = false,
    var isPassword: Boolean = false
)

// تبدیل اعداد انگلیسی به فارسی
fun convertEnglishDigitsToPersian(input: String): String {
    val englishDigits = '0'..'9'
    val persianDigits = listOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')

    val result = StringBuilder()
    for (char in input) {
        if (char in englishDigits) {
            val index = char - '0'
            result.append(persianDigits[index])
        } else {
            result.append(char)
        }
    }
    return result.toString()
}

// تبدیل اعداد فارسی به انگلیسی
fun convertPersianDigitsToEnglish(input: String): String {
    val persianDigits = listOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
    val englishDigits = '0'..'9'

    val result = StringBuilder()
    for (char in input) {
        val index = persianDigits.indexOf(char)
        if (index != -1) {
            result.append(englishDigits.elementAt(index))
        } else {
            result.append(char)
        }
    }
    return result.toString()
}


// اضافه کردن . هر 3 رقم برای نمایش مبلغ
fun formatWithDots(number: String, lang: String): String {
    val formatted = number.reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()

    return if (lang == "fa") convertEnglishDigitsToPersian(formatted) else formatted
}


