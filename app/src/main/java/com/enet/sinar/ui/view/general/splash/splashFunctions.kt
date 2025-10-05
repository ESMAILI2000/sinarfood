package com.enet.sinar.ui.view.general.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import com.enet.sinar.ui.theme.NationsBlue
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CustomLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = NationsBlue, // رنگ آبی
    numSegments: Int = 8, // تعداد "تکه‌های نور"
    segmentWidth: Float = 10f, // عرض هر تکه
    segmentLength: Float = 20f, // طول هر تکه
    innerRadiusFraction: Float = 0.5f // نسبت شعاع دایره مرکزی به کل اندازه
) {

    val infiniteTransition = rememberInfiniteTransition(label = "rotation_transition")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing), // 3 ثانیه برای یک چرخش کامل
            repeatMode = RepeatMode.Restart
        ), label = "rotation"
    )

    Canvas(modifier = modifier) {
        val center = size.center
        val actualSize = minOf(size.width, size.height)
        val radius = actualSize / 2
        val segmentGap = 360f / numSegments

        for (i in 0 until numSegments) {
            val angle = Math.toRadians((rotation + i * segmentGap).toDouble())
            val startX = center.x + radius * innerRadiusFraction * cos(angle).toFloat()
            val startY = center.y + radius * innerRadiusFraction * sin(angle).toFloat()
            val endX = center.x + (radius * innerRadiusFraction + segmentLength) * cos(angle).toFloat()
            val endY = center.y + (radius * innerRadiusFraction + segmentLength) * sin(angle).toFloat()

            drawLine(
                color = color,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = segmentWidth,
                cap = StrokeCap.Round // فقط سرها گرد می‌شن
            )
        }
    }

}

