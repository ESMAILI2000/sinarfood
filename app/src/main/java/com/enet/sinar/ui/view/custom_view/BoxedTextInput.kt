package com.enet.sinar.ui.view.custom_view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enet.sinar.ui.theme.Background

@Composable
fun BoxedTextInput(
    value: String,
    length: Int,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = LocalTextStyle.current,
    textBorderShape: Shape = RectangleShape,
    filledTextBorderColor: Color = Color.Black,
    emptyTextBorderColor: Color = Color.Gray,
    backgroundColor: Color = Background,
    textArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    with: Dp = 63.dp,
    height: Dp = 56.dp
) {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult: TextLayoutResult =
        textMeasurer.measure(
            text = AnnotatedString("W"),
            style = textStyle
        )
    val textSize = textLayoutResult.size
    val density = LocalDensity.current
    val size = with(density) { textSize.width.toDp() }

    BasicTextField(
        value = value,
        singleLine = true,
        onValueChange = {
            if (it.length <= length) {
                onValueChange(it)
            }
        },
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            Box {
                // hide the inner text field as we are dwelling the text field ourselves
                CompositionLocalProvider(
                    LocalTextSelectionColors.provides(
                        TextSelectionColors(
                            Color.Transparent,
                            Color.Transparent
                        )
                    )
                ) {
                    Box(modifier = Modifier.drawWithContent { }) {
                        innerTextField()
                    }
                }
                Row(
                    modifier,
                    horizontalArrangement = textArrangement,
                ) {
                    repeat(length) { index ->
                        val currentChar = value.getOrNull(index)
                        Box(
                            Modifier
                                .size(with,56.dp)
                                .background(if (currentChar != null) backgroundColor else Color.Transparent,textBorderShape)
                                .border(
                                    width = 1.dp,
                                    color = if (currentChar != null) filledTextBorderColor else emptyTextBorderColor,
                                    shape = textBorderShape
                                )
                        ) {
                            if (currentChar != null) {
                                Text(
                                    currentChar.toString(),
                                    Modifier
                                        .align(Alignment.Center)
                                        .wrapContentSize(unbounded = true),
                                    style = textStyle
                                )
                            }
                        }
                    }
                }
            }
        },
    )
}



@Preview(
    showBackground = true,
    apiLevel = 26,
    device = "spec:width=412dp,height=917dp,dpi=480"
)
@Composable
fun BoxedTextInputPreview() {
    Column {
        val x = rememberSaveable {
            mutableStateOf("12c4")
        }
        BoxedTextInput(
            value = x.value,
            length = 5,
            onValueChange = { x.value = it },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Black)
                .padding(16.dp),
            textStyle = TextStyle(fontSize = 48.sp),
            textBorderShape = CircleShape,
        )
        BoxedTextInput(
            value = "123",
            length = 4,
            onValueChange = { },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textBorderShape = CutCornerShape(8.dp),
        )
        BoxedTextInput(
            value = "1PS",
            length = 5,
            onValueChange = { },
            modifier = Modifier.padding(16.dp),
            textBorderShape = RoundedCornerShape(8.dp),
            textArrangement = Arrangement.spacedBy(16.dp),
        )
    }
}