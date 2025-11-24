package com.enet.sinar.ui.view.general.pay

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enet.sinar.R
import com.enet.sinar.ui.theme.Background
import com.enet.sinar.ui.theme.GrayC
import com.enet.sinar.ui.theme.Gunmetal
import com.enet.sinar.ui.theme.PoliceBlue
import com.enet.sinar.ui.theme.SinarTheme
import com.enet.sinar.ui.theme.Water
import com.enet.sinar.ui.theme.White
import com.enet.sinar.ui.utility.MySharedPreferences
import com.enet.sinar.ui.view.convertEnglishDigitsToPersian
import com.enet.sinar.ui.view.custom_view.EllipsizedMiddleText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(
    modifier: Modifier = Modifier,
    onNavigateToBack: () -> Unit = {}
){

    var walletAddress by remember { mutableStateOf("0x5A141B7eba38A151EDfcd816D912E6ae5C0307b1") }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val currentLang = MySharedPreferences.getLang(context)

    val layoutDirection = if (currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {

        BoxWithConstraints {
            val screenWidth = maxWidth
            val screenHeight = maxHeight

            Column(modifier = Modifier
                .fillMaxSize()
                .background(Background)
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.title_account_info),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W900),
                            modifier = Modifier
                                .padding(horizontal = 24.dp, vertical = 16.dp)
                        )},
                    actions = {
                        Icon(
                            imageVector = if (currentLang == "fa") Icons.Default.ArrowBack else Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Gunmetal,
                            modifier = Modifier
                                .padding(horizontal = 24.dp, vertical = 16.dp)
                                .size(24.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        onNavigateToBack()
                                    }
                                }
                        )
                    },
                    colors = TopAppBarDefaults. topAppBarColors(
                        containerColor = Background
                    )
                )
                Box(
                    modifier = Modifier
                        .padding(top = 1.dp)
                        .fillMaxSize()
                        .background(White, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
                    contentAlignment = Alignment.TopCenter
                ){
                    Column(
                        modifier = Modifier
                            .width(screenWidth * 0.883f)
                            .padding(top = screenHeight * 0.026f)
                            .border(1.dp, GrayC, RoundedCornerShape(16.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_qrcode) ,
                            contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(120.dp)
                                .padding(top = screenHeight * 0.021f)
                        )
                        Text(text = stringResource(id = R.string.label_wallet_address),
                            style = MaterialTheme.typography.bodySmall,
                            color = PoliceBlue,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(top = 16.dp, start = 20.dp)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(48.dp)
                                .padding(top = screenHeight * 0.004f)
                                .align(Alignment.CenterHorizontally)
                                .border(1.dp, Water, RoundedCornerShape(12.dp))
                                .background(Water.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        ){

                            Icon(
                                painter = painterResource(id = R.drawable.vc_copy),
                                contentDescription = "",
                                tint = Water,
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.CenterStart)
                                    .offset(x = 12.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            clipboardManager.setText(AnnotatedString(walletAddress))
                                            Toast
                                                .makeText(context, " کپی شد ", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    }
                            )

                            val walletAddressText = if (currentLang == "fa") convertEnglishDigitsToPersian(walletAddress) else walletAddress
                            EllipsizedMiddleText(text = walletAddressText,
                                startLength = 5,
                                endLength = 6,
                                maxLines = 1,
                                color = Gunmetal,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.vc_wallet),
                                contentDescription = "",
                                tint = Water,
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.CenterEnd)
                                    .offset(x = -12.dp)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .align(Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            HorizontalDivider(
                                modifier = Modifier.width(44.dp),
                                thickness = 1.dp,
                                color = GrayC
                            )
                            Text(text = stringResource(id = R.string.label_share_with),
                                style = MaterialTheme.typography.labelSmall,
                                color = Gunmetal
                            )
                            HorizontalDivider(
                                modifier = Modifier.width(44.dp),
                                thickness = 1.dp,
                                color = GrayC
                            )

                        }

                        Row(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .align(Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_sms) ,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            val smsIntent = Intent(Intent.ACTION_VIEW).apply {
                                                data = Uri.parse("sms:")
                                                putExtra("sms_body", walletAddress)
                                            }
                                            context.startActivity(smsIntent)
                                        }
                                    }
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_whatsapp) ,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            shareText(context, walletAddress, "com.whatsapp")
                                        }
                                    }
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_telegram) ,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            shareText(
                                                context,
                                                walletAddress,
                                                "org.telegram.messenger"
                                            )
                                        }
                                    }
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_instagram) ,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            shareText(
                                                context,
                                                walletAddress,
                                                "com.instagram.android"
                                            )
                                        }
                                    }
                            )

                        }

                        Spacer(modifier = Modifier.height(screenHeight * 0.021f))
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
fun InformationScreenPreview() {
    SinarTheme {
        InformationScreen()
    }
}