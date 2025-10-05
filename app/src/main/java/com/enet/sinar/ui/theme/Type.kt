package com.enet.sinar.ui.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.material3.Typography
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.enet.sinar.R
import com.enet.sinar.ui.utility.MySharedPreferences


val default = FontFamily(
        /*
        * This can be any font that makes sense
        */
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

val bodyLargeStyleEnglish = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.sp
)

val bodyLargeStylePersian = TextStyle(
        fontFamily = iranSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.sp
)

val Typography = Typography(
        bodyLarge = TextStyle(
        fontFamily = iranSansFamily,
                fontWeight = FontWeight.Medium,
                 fontSize = 14.sp,
                 letterSpacing = 0.sp
         ),

        displayLarge = TextStyle(
                fontFamily = displayLargeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Gunmetal
        ),
        bodySmall = TextStyle(
                fontFamily = iranSansFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
        ),
        labelSmall = TextStyle(
                fontFamily = iranSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                lineHeight = 8.sp,
                letterSpacing = 0.5.sp
        ),
        labelLarge = TextStyle(
                fontFamily = iranSansFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
        ),
        titleLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
        ),
        displayMedium = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
        )
        /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    */
)