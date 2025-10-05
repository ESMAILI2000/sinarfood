package com.enet.sinar.ui.view.custom_view


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.enet.sinar.R
import com.enet.sinar.ui.view.BottomBarItem

@Composable
fun CustomBottomNavigationBar(
    items: List<BottomBarItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp), // ارتفاع کل نوار
        contentAlignment = Alignment.BottomCenter
    ) {
        // نوار آبی رنگ
        Box(
            modifier = Modifier
                .width(380.dp)
                .height(76.dp)
                .background(Color(0xFFB3E5FC), RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
        )

        // آیتم‌ها
        Row(
            modifier = Modifier
                .width(380.dp)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            items.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .width(56.dp) // قسمت زرد رنگ
                        .height(76.dp)
                        .clickable { onItemSelected(index) },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    // نیم‌دایره فقط برای آیتم انتخاب‌شده
                    androidx.compose.animation.AnimatedVisibility(visible = index == selectedIndex) {
                        Image(
                            painter = painterResource(id = R.drawable.vc_half_circle),
                            contentDescription = null,
                            modifier = Modifier
                                .size(width = 109.dp, height = 58.dp)
                                .align(Alignment.TopCenter)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Icon(
                            painter = if (index == selectedIndex) item.selectedIcon else item.icon,
                            contentDescription = item.text,
                            modifier = Modifier.size(24.dp),
                            tint = if (index == selectedIndex) Color.White else Color.Gray
                        )
                        Text(
                            text = item.text,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (index == selectedIndex) Color.White else Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

