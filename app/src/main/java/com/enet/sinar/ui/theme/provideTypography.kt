package com.enet.sinar.ui.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.enet.sinar.R
import com.enet.sinar.ui.utility.MySharedPreferences

@Composable
fun provideTypography(): Typography {
    val context = LocalContext.current
    val currentLocale = MySharedPreferences.getLang(context)
    val isEnglish = currentLocale == "en"

    // گرفتن اطلاعات صفحه نمایش
    val displayMetrics = context.resources.displayMetrics
    val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    val screenheightDp = displayMetrics.heightPixels / displayMetrics.density

    // تنظیم اندازه فونت بر اساس سایز صفحه

    val baseFontSize20 = when {
        screenheightDp >= 800 -> 20.sp // گوشی‌های متوسط
        else -> 18.sp // گوشی‌های کوچک
    }

    val baseFontSize18 = when {
        screenheightDp >= 800 -> 18.sp
        else -> 16.sp
    }

    val baseFontSize16 = when {
        screenheightDp >= 800 -> 16.sp
        else -> 14.sp
    }

    val baseFontSize14 = when {
        screenheightDp >= 800 -> 14.sp
        else -> 12.sp
    }

    val baseFontSize12 = when {
        screenheightDp >= 800 -> 12.sp
        else -> 10.sp
    }

    val default = FontFamily(
        Font(R.font.ir_black)
    )
    @OptIn(ExperimentalTextApi::class)
    val displayLargeFontFamily = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        FontFamily(Font(R.font.ir_black))
    } else {
        default
    }

// Set of Material typography styles to start with
    val iranSansFamily = FontFamily(
        Font(R.font.ir_black, FontWeight.Black),
        Font(R.font.ir_bold, FontWeight.Bold),
        Font(R.font.ir_demibold, FontWeight.SemiBold),
        Font(R.font.ir_extrablack, FontWeight.W900),
        Font(R.font.ir_extrabold, FontWeight.ExtraBold),
        Font(R.font.ir_heavy, FontWeight.W900),
        Font(R.font.ir_light, FontWeight.Light),
        Font(R.font.ir_medium, FontWeight.Medium),
        Font(R.font.ir_regular, FontWeight.Normal),
        Font(R.font.ir_thin, FontWeight.Thin),
        Font(R.font.ir_ultralight, FontWeight.ExtraLight),
    )
    val bodyLargeStyle = if (isEnglish) {
        TextStyle(
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            fontWeight = FontWeight.Medium,
            fontSize = baseFontSize14,
            letterSpacing = 0.sp
        )
    } else {
        TextStyle(
            fontFamily = iranSansFamily,
            fontWeight = FontWeight.Medium,
            fontSize = baseFontSize14,
            letterSpacing = 0.sp
        )
    }

    return Typography(
        bodyLarge = bodyLargeStyle,
        displayLarge = TextStyle(
            fontFamily = displayLargeFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = baseFontSize18,
            color = Gunmetal
        ),
        titleMedium = TextStyle(
            fontFamily = iranSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = baseFontSize16,
            color = Gunmetal
        ),
        bodyMedium = TextStyle (
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp
        ),
        bodySmall = TextStyle(
            fontFamily = iranSansFamily,
            fontWeight = FontWeight.Medium,
            fontSize = baseFontSize12,
        ),
        labelSmall = TextStyle(
            fontFamily = iranSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = baseFontSize12,
        ),
        labelLarge = TextStyle(
            fontFamily = iranSansFamily,
            fontWeight = FontWeight.Medium,
            fontSize = baseFontSize20
        ),
        labelMedium = TextStyle(
            fontFamily = iranSansFamily,
            fontWeight = FontWeight.Medium,
            fontSize = baseFontSize16
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily(Font(R.font.ir_extrablack)),
            fontWeight = FontWeight.W900,
            fontSize = baseFontSize20,
        ),
        displayMedium = TextStyle(
            fontFamily = iranSansFamily,
            fontWeight = FontWeight.Medium,
            fontSize = baseFontSize18,
            letterSpacing = 0.sp
        )
    )
}
