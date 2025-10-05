package com.enet.sinar.ui.view.general.login

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enet.sinar.R
import com.enet.sinar.ui.theme.Background
import com.enet.sinar.ui.theme.GrayC
import com.enet.sinar.ui.theme.Primary

// تغیر صفحه لاگین از شهروند به دانشگاهی و بالعکس
@Composable
fun RoleSelector(
    selectedRole: Boolean,
    widthItem: Dp,
    heightItem: Dp,
    onRoleSelected: (Boolean) -> Unit
) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
                val isSelected = selectedRole
            Button(
                onClick = { onRoleSelected(false) },
                Modifier
                    .fillMaxWidth(0.45f)
                    .height(heightItem),
                colors = ButtonDefaults.buttonColors(
                    if (!isSelected) Background else androidx.compose.ui.graphics.Color.Transparent,
                    if (!isSelected) androidx.compose.ui.graphics.Color.White else androidx.compose.ui.graphics.Color.Black,
                    androidx.compose.ui.graphics.Color.Black,
                    androidx.compose.ui.graphics.Color.Black,
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (!isSelected) Primary else GrayC
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = stringResource(id = R.string.citizens),
                    color = if (isSelected) GrayC else Primary,
                    fontFamily = FontFamily(Font(R.font.ir_medium)),
                    fontSize = 12.sp)
            }

//            Spacer(modifier = Modifier.width(heightItem/3))

            Button(
                onClick = { onRoleSelected(true) },
                Modifier
                    .fillMaxWidth(0.81f)
                    .size(widthItem,heightItem),
                colors = ButtonDefaults.buttonColors(
                    if (isSelected) Background else androidx.compose.ui.graphics.Color.Transparent,
                    if (isSelected) androidx.compose.ui.graphics.Color.White else androidx.compose.ui.graphics.Color.Black,
                    androidx.compose.ui.graphics.Color.Black,
                    androidx.compose.ui.graphics.Color.Black,
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isSelected) Primary else GrayC
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = stringResource(id = R.string.academics),
                    color = if (isSelected) Primary else GrayC,
                    fontFamily = FontFamily(Font(R.font.ir_medium)),
                    fontSize = 12.sp)
            }
        }
}

// ساخت تصویر کپچا
@Composable
fun CaptchaImage(captchaText: String,widthItem: Float,heightItem: Float) {
    val density = LocalDensity.current

    val imageBitmap = remember(captchaText) {
        val width = widthItem.toInt()
        val height = heightItem.toInt()
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // پس‌زمینه
        canvas.drawColor(Color.WHITE)

        val textSizeInSp = 20.sp
        val textSizePx = with(density) { textSizeInSp.toPx() }

        val paint = Paint().apply {
            color = Color.BLUE
            textSize = textSizePx
            isAntiAlias = true
            typeface = Typeface.DEFAULT_BOLD
        }

        canvas.drawText(captchaText, 20f, 90f, paint)

        // خطوط امنیتی تصادفی
        val linePaint = Paint().apply {
            color = Color.DKGRAY
            strokeWidth = 2f
        }
        repeat(5) {
            val startX = (0..width).random().toFloat()
            val startY = (0..height).random().toFloat()
            val endX = (0..width).random().toFloat()
            val endY = (0..height).random().toFloat()
            canvas.drawLine(startX, startY, endX, endY, linePaint)
        }

        bitmap.asImageBitmap()
    }

    Image(
        bitmap = imageBitmap,
        contentDescription = "Captcha",
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures {
                    generateCaptcha(6)
                }
            }
    )
}

// ساخت کد کپچا
fun generateCaptcha(length: Int = 6): String {
    val upperCase = ('A'..'Z')
    val lowerCase = ('a'..'z')
    val digits = ('0'..'9')
    val all = upperCase + lowerCase + digits

    val captcha = mutableListOf<Char>()

    // حداقل یک حرف بزرگ و یک عدد
    captcha += upperCase.random()
    captcha += digits.random()

    // بقیه حروف تصادفی از کل مجموعه
    repeat(length - 2) {
        captcha += all.random()
    }

    // مخلوط کردن حروف برای طبیعی‌تر بودن
    return captcha.shuffled().joinToString("")
}