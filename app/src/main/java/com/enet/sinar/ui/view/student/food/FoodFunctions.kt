package com.enet.sinar.ui.view.student.food

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class FoodItem(
    val foodName:String,
    val resturanName: String,
    val isMen: Boolean,
    val image: Painter,
    val color: Color
)

@SuppressLint("InvalidColorHexValue")
val gradientBrush = Brush.linearGradient(
    colors = listOf(Color(0xFF66666600), Color(0xFF000000BA).copy(alpha = 0.73f)),
    start = Offset(0f, 0f),
    end = Offset(1000f, 1000f)
)


