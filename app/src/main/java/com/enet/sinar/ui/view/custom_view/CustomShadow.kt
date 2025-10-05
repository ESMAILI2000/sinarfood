package com.enet.sinar.ui.view.custom_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.enet.sinar.ui.theme.NationsBlue

fun Modifier.customShadow(
    elevation: Dp,
    shape: Shape = RoundedCornerShape(12.dp),
    clip: Boolean = true,
    ambientColor: Color = NationsBlue, // رنگ سایه دلخواه
    spotColor: Color = NationsBlue     // رنگ سایه دلخواه
): Modifier = this.then(
    Modifier.shadow(
        elevation = elevation,
        shape = shape,
        clip = clip,
        ambientColor = ambientColor,
        spotColor = spotColor
    )
)

fun Modifier.customShadowCompat(
    color: Color = Color.Red,
    alpha: Float = 0.5f,
    cornerRadius: Dp = 8.dp,
    shadowRadius: Dp = 12.dp,
    offsetY: Dp = 4.dp
): Modifier = this.then(
    Modifier.drawBehind {
        val shadowColor = color.copy(alpha = alpha)
        drawRoundRect(
            color = shadowColor,
            topLeft = Offset(0f, offsetY.toPx()),
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        )
    }
)


@Preview(
    showBackground = true,
    apiLevel = 26,
    device = "spec:width=412dp,height=917dp,dpi=480"
)
@Composable
fun CoustomShadowPreview() {
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .customShadow(elevation = 8.dp, ambientColor = Color.Red, spotColor = Color.Red),
            shape = RoundedCornerShape(8.dp),
//            elevation = 0.dp // چون سایه رو خودت اعمال کردی
        ) {
            Text(text = "salam")
        }
    }
}
