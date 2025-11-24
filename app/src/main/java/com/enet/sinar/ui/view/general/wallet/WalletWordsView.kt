package com.enet.sinar.ui.view.general.wallet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enet.sinar.R
import com.enet.sinar.ui.theme.Background
import com.enet.sinar.ui.theme.GrayB
import com.enet.sinar.ui.theme.GrayF
import com.enet.sinar.ui.theme.Gunmetal
import com.enet.sinar.ui.theme.NationsBlue
import com.enet.sinar.ui.theme.PoliceBlue
import com.enet.sinar.ui.theme.SinarTheme
import com.enet.sinar.ui.utility.MySharedPreferences

@Composable
fun WalletWordsScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    val clipboardManager = LocalClipboardManager.current
    val texts = mutableListOf<String>().apply {
        add("flame")
        add("rookie")
        add("picnic")
        add("brush")
        add("frame")
        add("alpha")
        add("autdoor")
        add("confirm")
        add("chen")
        add("drive")
        add("brunch")
        add("maze")
    }
    val context = LocalContext.current
    val currentLang = MySharedPreferences.getLang(context)

    val layoutDirection = if (currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection){
        BoxWithConstraints (
            modifier = Modifier
//                .padding(
//                    bottom = WindowInsets.navigationBars
//                        .asPaddingValues()
//                        .calculateBottomPadding() + 0.dp
//                )
                .fillMaxSize()
        ) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight

            Column(
                Modifier
                    .fillMaxWidth(0.88f)
                    .padding(bottom = screenHeight * 0.043f)
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(screenHeight * 0.008f)
            ) {
                Button(
                    onClick = {
                        onNavigateToHome()
                    },
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(screenWidth * 0.8f, screenHeight * 0.061f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults. buttonColors(
                        containerColor = NationsBlue,
                        contentColor = Color.White,
                    )
                ) {
                    Icon(imageVector = if (currentLang == "fa") Icons.Default.ArrowForward else Icons.Default.ArrowBack , contentDescription = null )
                    Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                    Text(stringResource(id = R.string.next_step),
                        fontFamily = FontFamily(Font(R.font.ir_medium)),
                        fontSize = 14.sp)
                }
                OutlinedButton(
                    onClick = {},
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(screenWidth * 0.8f, screenHeight * 0.061f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, NationsBlue)
                ) {
                    Icon(painter = painterResource(id = R.drawable.vc_download) , contentDescription = null )
                    Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                    Text(stringResource(id = R.string.save_changes),
                        fontFamily = FontFamily(Font(R.font.ir_medium)),
                        fontSize = 14.sp)
                     }
            }

            Icon(
                imageVector = if (currentLang == "fa") Icons.Default.ArrowBack else Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Gunmetal,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .pointerInput(Unit){
                        detectTapGestures {
                            onNavigateToBack()
                        }
                    }
                    .padding(horizontal = screenWidth * 0.06f, vertical = screenHeight * 0.07f)
            )

            Column(
                Modifier
                    .fillMaxWidth(0.88f)
                    .align(Alignment.TopCenter)
                    .padding(top = screenHeight * 0.096f)
            ) {
                val image = painterResource(R.drawable.ic_wallet)
                Image(
                    painter = image, contentDescription = "logo",
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(105.dp, screenHeight * 0.113f)
                )
                Text(
                    text = stringResource(id = R.string.write_down_your_recovery),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = screenHeight * 0.008f),
                    fontSize = 20.sp,
                    color = Gunmetal
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
//                    .height(44.dp)
                        .padding(top = screenHeight * 0.008f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.save_your_recovery_phrase_either),
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.labelSmall.copy(lineHeight = 16.sp ),
                        color = GrayF,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(id = R.string.enter_this_phrase),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = screenWidth * 0.02f),
                        style = MaterialTheme.typography.labelSmall,
                        color = GrayF,
                        textAlign = TextAlign.Center
                    )
                }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screenHeight * 0.257f)
                            .padding(top = screenHeight * 0.026f)
                            .align(Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // تقسیم لیست به گروه‌های ۳ تایی
                        texts.chunked(3).forEachIndexed { rowIndex, rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                rowItems.forEachIndexed { columnIndex, item ->
                                    Box(
                                        modifier = Modifier
                                            .width(screenWidth * 0.26f)
                                            .height(screenHeight * 0.048f)
                                            .background(Background, RoundedCornerShape(12.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${rowIndex * 3 + columnIndex + 1}. $item",
                                            textAlign = TextAlign.Center,
                                            color = Color.Black,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }
                        }
                    }
                }


                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = screenHeight * 0.037f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.vc_wallet), contentDescription = "")
                    Text(text = stringResource(id = R.string.your_wallet_address),
                        maxLines = 1,
                        color = PoliceBlue,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
//                            .fillMaxWidth(0.8f)
                            .padding(start = screenWidth * 0.02f))
                }

                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.095f)
                        .padding(top = screenHeight * 0.008f)
                        .background(GrayB, RoundedCornerShape(12.dp))
                        .pointerInput(Unit){
                            detectTapGestures {
                                clipboardManager.setText(AnnotatedString("0x5A141B7eba38A151EDfcd816D912E6ae5C0307b1"))
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "0x5A141B7eba38A151EDfcd86ae5C0307b1",
                        maxLines = 1,
                        color = Gunmetal,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth())

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = screenHeight * 0.017f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(id = R.string.copy),
                            maxLines = 1,
                            color = Gunmetal,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .padding(end = screenWidth * 0.02f))
                        Icon(painter = painterResource(id = R.drawable.vc_copy),
                            contentDescription = "",
                            Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }

}


@Preview(
    showBackground = true,
    apiLevel = 26,
    device = "spec:width=412dp,height=917dp,dpi=480"
)
@Composable
fun WalletWordsPreview() {
    SinarTheme {
        WalletWordsScreen()
    }
}