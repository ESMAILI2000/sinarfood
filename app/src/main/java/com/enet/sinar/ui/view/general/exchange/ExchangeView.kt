package com.enet.sinar.ui.view.general.exchange

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.enet.sinar.R
import com.enet.sinar.ui.theme.Background
import com.enet.sinar.ui.theme.EerieBlack
import com.enet.sinar.ui.theme.Gray04
import com.enet.sinar.ui.theme.GrayC
import com.enet.sinar.ui.theme.GrayD
import com.enet.sinar.ui.theme.GrayF
import com.enet.sinar.ui.theme.Gunmetal
import com.enet.sinar.ui.theme.NationsBlue
import com.enet.sinar.ui.theme.PoliceBlue
import com.enet.sinar.ui.theme.SinarTheme
import com.enet.sinar.ui.theme.Succes
import com.enet.sinar.ui.theme.Water
import com.enet.sinar.ui.theme.White
import com.enet.sinar.ui.utility.MySharedPreferences
import com.enet.sinar.ui.view.convertEnglishDigitsToPersian
import com.enet.sinar.ui.view.custom_view.DashedContainer
import com.enet.sinar.ui.view.custom_view.EllipsizedMiddleText
import com.enet.sinar.ui.view.custom_view.HexagonShape
import com.enet.sinar.ui.view.custom_view.customShadow
import com.enet.sinar.ui.view.custom_view.customShadowCompat
import com.enet.sinar.ui.view.formatWithDots


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeScreen(
    modifier: Modifier = Modifier,
    onNavigateToBack: () -> Unit = {},
){

    var state by remember { mutableStateOf(0) } // 0-> انتقال توکن  1-> خرید توکن  2-> فروش توکن  3-> شارژ باتری
    val context = LocalContext.current

    var isBatriLowRadio by remember { mutableStateOf(false) }
    var isDialogTransactionSuccess by remember { mutableStateOf(false) }
    var isDialogBuySuccess by remember { mutableStateOf(false) }
    var isDialogSellSuccess by remember { mutableStateOf(false) }
    var isCardLowRadio by remember { mutableStateOf(true) }
    var amountToken by remember { mutableStateOf("") }
    var walletAddress by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val itemsOffers = listOf(
        OffersItem(3,"sinar",160,30,50),
        OffersItem(50,"avsin",15,40,5),
    )

    val currentLang = MySharedPreferences.getLang(context)

    val layoutDirection = if (currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {

        BoxWithConstraints (
            modifier = Modifier
                .background(Background)
                .fillMaxSize()
        ) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight

            // دیالوگ انتقال موفقیت آمیز توکن
            if (isDialogTransactionSuccess){
                Dialog(
                    onDismissRequest = {
                        isDialogTransactionSuccess = false
                    },
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    Column(
                        modifier = Modifier
                            .width(screenWidth * 0.883f)
                            .background(White, RoundedCornerShape(32.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "" ,
                            tint = EerieBlack,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 16.dp, top = screenHeight * 0.017f)
                                .size(24.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        isDialogTransactionSuccess = false
                                    }
                                }
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.vc_success),
                                contentDescription = "",
                                Modifier.size(68.dp)
                            )

                            Spacer(modifier = Modifier.height(screenHeight * 0.017f))

                            CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                                Text(
                                    text = stringResource(id = R.string.transfer_successfully),
                                    style = MaterialTheme.typography.displayMedium,
                                    color = Gunmetal,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(screenHeight * 0.017f))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.transaction_serial),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = PoliceBlue
                                    )
                                    EllipsizedMiddleText(
                                        text = "0x5A141B7eba38A151EDfcd816D912E6ae5C0307b1",
                                        startLength = 8,
                                        endLength = 7,
                                        maxLines = 1,
                                        color = PoliceBlue,
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.vc_serial),
                                        contentDescription = "",
                                        tint = PoliceBlue,
                                        modifier =  Modifier.size(24.dp)
                                    )

                                }
                            }

                            Spacer(modifier = Modifier.height(screenHeight * 0.026f))

                            Button(
                                onClick = {},
                                Modifier
                                    .size(screenWidth * 0.8f, screenHeight * 0.061f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults. buttonColors(
                                    containerColor = NationsBlue,
                                    contentColor = Color.White,
                                )
                            ) {
                                Text(stringResource(id = R.string.view_explorer),
                                    fontFamily = FontFamily(Font(R.font.ir_medium)),
                                    fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                Icon(painter = painterResource(id = R.drawable.vc_world) , contentDescription = null )
                            }

                            Spacer(modifier = Modifier.height(screenHeight * 0.017f))
                        }
                    }
                }
            }

            // دیالوگ خرید موفقیت آمیز توکن
            if (isDialogBuySuccess){
                Dialog(
                    onDismissRequest = {
                        isDialogBuySuccess = false
                    },
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    Column(
                        modifier = Modifier
                            .width(screenWidth * 0.883f)
                            .background(White, RoundedCornerShape(32.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "" ,
                            tint = EerieBlack,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 16.dp, top = screenHeight * 0.017f)
                                .size(24.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        isDialogBuySuccess = false
                                    }
                                }
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.vc_success),
                                contentDescription = "",
                                Modifier.size(68.dp)
                            )

                            CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                                Spacer(modifier = Modifier.height(screenHeight * 0.017f))

                                Text(
                                    text = stringResource(id = R.string.buy_successfully),
                                    style = MaterialTheme.typography.displayMedium,
                                    color = Gunmetal,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(screenHeight * 0.017f))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.transaction_serial),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = PoliceBlue
                                    )
                                    EllipsizedMiddleText(
                                        text = "0x5A141B7eba38A151EDfcd816D912E6ae5C0307b1",
                                        startLength = 8,
                                        endLength = 7,
                                        maxLines = 1,
                                        color = PoliceBlue,
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.vc_serial),
                                        contentDescription = "",
                                        tint = PoliceBlue,
                                        modifier =  Modifier.size(24.dp)
                                    )

                                }
                            }

                            Spacer(modifier = Modifier.height(screenHeight * 0.026f))

                            Button(
                                onClick = {},
                                Modifier
                                    .size(screenWidth * 0.8f, screenHeight * 0.061f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults. buttonColors(
                                    containerColor = NationsBlue,
                                    contentColor = Color.White,
                                )
                            ) {
                                Text(stringResource(id = R.string.view_explorer),
                                    fontFamily = FontFamily(Font(R.font.ir_medium)),
                                    fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                Icon(painter = painterResource(id = R.drawable.vc_world) , contentDescription = null )
                            }

                            Spacer(modifier = Modifier.height(screenHeight * 0.017f))
                        }
                    }
                }
            }

            // دیالوگ فروش موفقیت آمیز توکن
            if (isDialogSellSuccess){
                Dialog(
                    onDismissRequest = {
                        isDialogSellSuccess = false
                    },
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    Column(
                        modifier = Modifier
                            .width(screenWidth * 0.883f)
                            .background(White, RoundedCornerShape(32.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "" ,
                            tint = EerieBlack,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 16.dp, top = screenHeight * 0.017f)
                                .size(24.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        isDialogSellSuccess = false
                                    }
                                }
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.vc_success),
                                contentDescription = "",
                                Modifier.size(68.dp)
                            )

                            CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                                Spacer(modifier = Modifier.height(screenHeight * 0.017f))

                                Text(
                                    text = stringResource(id = R.string.sell_successfully),
                                    style = MaterialTheme.typography.displayMedium,
                                    color = Gunmetal,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(screenHeight * 0.004f))

                                Text(
                                    text = stringResource(id = R.string.transaction_deposit),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = GrayF
                                )

                                Spacer(modifier = Modifier.height(screenHeight * 0.009f))

                                Text(
                                    text = stringResource(id = R.string.deposit_notification),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = PoliceBlue,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(0.8f)
                                )

                                Spacer(modifier = Modifier.height(screenHeight * 0.017f))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.transaction_serial),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = PoliceBlue
                                    )
                                    EllipsizedMiddleText(
                                        text = "0x5A141B7eba38A151EDfcd816D912E6ae5C0307b1",
                                        startLength = 8,
                                        endLength = 7,
                                        maxLines = 1,
                                        color = PoliceBlue,
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.vc_serial),
                                        contentDescription = "",
                                        tint = PoliceBlue,
                                        modifier =  Modifier.size(24.dp)
                                    )

                                }
                            }

                            Spacer(modifier = Modifier.height(screenHeight * 0.026f))

                            Button(
                                onClick = {},
                                Modifier
                                    .size(screenWidth * 0.8f, screenHeight * 0.061f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults. buttonColors(
                                    containerColor = NationsBlue,
                                    contentColor = Color.White,
                                )
                            ) {
                                Text(stringResource(id = R.string.view_explorer),
                                    fontFamily = FontFamily(Font(R.font.ir_medium)),
                                    fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                Icon(painter = painterResource(id = R.drawable.vc_world) , contentDescription = null )
                            }

                            Spacer(modifier = Modifier.height(screenHeight * 0.017f))
                        }
                    }
                }
            }

            // محتوای اصلی صفحه
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Background)
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides if (currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr){
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = R.string.exchange),
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
                }

                Column(
                    modifier = Modifier
                        .padding(top = 1.dp)
                        .fillMaxSize()
                        .background(White, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f)
                ){
                    Spacer(modifier = Modifier.height(screenHeight * 0.026f))

//                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.88f),
//                                .height(94.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(
                            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.013f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DashedContainer(
                                modifier = Modifier
                                    .size(64.dp)
                                    .background(White, shape = RoundedCornerShape(32.dp)),
                                cornerRadius = 32.dp,
                                borderColor = if (state == 0) Water else GrayD,
                                strokeWidth = 1.dp,
                                gapLength = if (state == 0) 0.dp else 8.dp
                            ){
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            state = 0
                                        }
                                    }
                                    .background(
                                        if (state == 0) NationsBlue.copy(alpha = 0.1f) else White,
                                        CircleShape
                                    ),
                                    contentAlignment = Alignment.Center
                                ){
                                    Icon(painter = painterResource(id = R.drawable.vc_arrow_top) ,
                                        contentDescription = "",
                                        tint = if (state == 0) NationsBlue else GrayC,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .padding(2.dp)
                                    )
                                }
                            }

                            Text(
                                text = stringResource(id = R.string.token_transfer),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (state == 0) NationsBlue else GrayD
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.013f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DashedContainer(
                                modifier = Modifier
                                    .size(64.dp)
                                    .background(White, shape = RoundedCornerShape(32.dp)),
                                cornerRadius = 32.dp,
                                borderColor = if (state == 1) Water else GrayD,
                                strokeWidth = 1.dp,
                                dashLength = 4.dp,
                                gapLength = if (state == 1) 0.dp else 5.dp
                            ){
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            state = 1
                                        }
                                    }
                                    .background(
                                        if (state == 1) NationsBlue.copy(alpha = 0.1f) else White,
                                        CircleShape
                                    ),
                                    contentAlignment = Alignment.Center
                                ){
                                    Icon(painter = painterResource(id = R.drawable.vc_arrow_right_top) ,
                                        contentDescription = "",
                                        tint = if (state == 1) NationsBlue else GrayC,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .padding(2.dp)
                                    )
                                }
                            }

                            Text(
                                text = stringResource(id = R.string.token_buy),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (state == 1) NationsBlue else GrayD
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.013f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DashedContainer(
                                modifier = Modifier
                                    .size(64.dp)
                                    .background(White, shape = RoundedCornerShape(32.dp)),
                                cornerRadius = 32.dp,
                                borderColor = if (state == 2) Water else GrayD,
                                strokeWidth = 1.dp,
                                gapLength = if (state == 2) 0.dp else 8.dp
                            ){
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            state = 2
                                        }
                                    }
                                    .background(
                                        if (state == 2) NationsBlue.copy(alpha = 0.1f) else White,
                                        CircleShape
                                    ),
                                    contentAlignment = Alignment.Center
                                ){
                                    Icon(painter = painterResource(id = R.drawable.vc_arrow_left_bottom) ,
                                        contentDescription = "",
                                        tint = if (state == 2) NationsBlue else GrayC,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .padding(2.dp)
                                    )
                                }
                            }

                            Text(
                                text = stringResource(id = R.string.token_sell),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (state == 2) NationsBlue else GrayD
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.013f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DashedContainer(
                                modifier = Modifier
                                    .size(64.dp)
                                    .background(White, shape = RoundedCornerShape(32.dp)),
                                cornerRadius = 32.dp,
                                borderColor = if (state == 3) Water else GrayD,
                                strokeWidth = 1.dp,
                                gapLength = if (state == 3) 0.dp else 8.dp
                            ){
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            state = 3
                                        }
                                    }
                                    .background(
                                        if (state == 3) NationsBlue.copy(alpha = 0.1f) else White,
                                        CircleShape
                                    ),
                                    contentAlignment = Alignment.Center
                                ){
                                    Icon(painter = painterResource(id = R.drawable.vc_batri) ,
                                        contentDescription = "",
                                        tint = if (state == 3) NationsBlue else GrayC,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .padding(2.dp)
                                    )
                                }
                            }

                            Text(
                                text = stringResource(id = R.string.battery_charging),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (state == 3) NationsBlue else GrayD
                            )
                        }

                    }
//                    }


                    Spacer(modifier = Modifier.size(screenHeight * 0.001f))

                    Column(
                        modifier = Modifier
                    ) {
                        if (state == 3){
                            Column(
                                Modifier
                                    .fillMaxWidth(0.88f)
                                    .verticalScroll(scrollState)
                                    .border(1.dp, GrayC, RoundedCornerShape(16.dp)),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(screenHeight * 0.021f))

                                Card(
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 6.dp,
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = White
                                    ),
                                    modifier = Modifier
                                        .width(screenWidth * 0.786f)
                                        .height(60.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(White, RoundedCornerShape(12.dp)),
//                                        .width(screenWidth * 0.786f)
//                                        .height(60.dp),
                                        horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.04f)
                                    ) {
                                        Row(modifier = Modifier
                                            .width(screenWidth * 0.373f)
                                            .height(60.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start
                                        ){
                                            Icon(
                                                painter = painterResource(id = R.drawable.vc_user),
                                                contentDescription = "",
                                                tint = PoliceBlue,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                            Text(text = stringResource(id = R.string.token_from_account),
                                                style = MaterialTheme.typography.bodySmall,
                                                color = PoliceBlue
                                            )
                                            Spacer(modifier = Modifier.width(screenWidth * 0.04f))
                                        }

                                        Row(modifier = Modifier
                                            .width(screenWidth * 0.373f)
                                            .height(60.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ){
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_avatar),
                                                contentDescription ="",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(32.dp)
                                                    .clip(HexagonShape())
                                            )
                                            Text(text = if (currentLang == "fa") "ریحانه کشاورز حداد" else "Reyhaneh keshavarz",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Gunmetal
                                            )
                                            Icon(
                                                imageVector = if (currentLang == "fa") Icons.Default.KeyboardArrowLeft else Icons.Default.KeyboardArrowRight,
                                                contentDescription = "",
                                                tint = EerieBlack,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                    }
                                }

                                Spacer(modifier = Modifier.height(screenHeight * 0.026f))

                                Card(
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 6.dp,
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = White
                                    ),
                                    modifier = Modifier
                                        .width(screenWidth * 0.786f)
                                ){
                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .background(White, RoundedCornerShape(12.dp)),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(modifier = Modifier.height(screenHeight * 0.017f))
                                        Row(
                                            Modifier.width(screenWidth * 0.71f),
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            Text(text = stringResource(id = R.string.token_amount_for_charging),
                                                style = MaterialTheme.typography.bodySmall,
                                                color = PoliceBlue)

                                            Text(text = " (${stringResource(id = R.string.app_name)})",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = NationsBlue)
                                        }

                                        Spacer(modifier = Modifier.height(screenHeight * 0.004f))

                                        val amountTokenText = if (currentLang == "fa") convertEnglishDigitsToPersian(amountToken) else amountToken
                                        TextField(
                                            value = amountTokenText,
                                            onValueChange ={
                                                amountToken = it
                                            },
                                            textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center),
                                            leadingIcon = {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.vc_b_price) , contentDescription = "" ,
                                                    tint = NationsBlue,
                                                    modifier = Modifier
                                                        .size(24.dp)
                                                )
                                            },
                                            singleLine = true,
                                            shape = RoundedCornerShape(12.dp),
                                            colors =  TextFieldDefaults.colors(
                                                focusedContainerColor = NationsBlue.copy(alpha = 0.1f),
                                                unfocusedContainerColor = NationsBlue.copy(alpha = 0.1f),
                                                focusedTextColor = NationsBlue,
                                                unfocusedTextColor = Gunmetal,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent
                                            ),
                                            keyboardOptions = KeyboardOptions(
                                                autoCorrect = false,
                                                keyboardType = KeyboardType.Number
                                            ),
                                            modifier = Modifier
                                                .width(screenWidth * 0.71f)
                                                .height(48.dp)
                                                .border(
                                                    1.dp,
                                                    NationsBlue,
                                                    RoundedCornerShape(12.dp)
                                                )
                                        )

                                        Spacer(modifier = Modifier.height(screenHeight * 0.017f))

                                        Button(
                                            onClick = {},
                                            Modifier
                                                .align(Alignment.CenterHorizontally)
                                                .width(screenWidth * 0.71f)
                                                .height(48.dp),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = ButtonDefaults. buttonColors(
                                                containerColor = NationsBlue,
                                                contentColor = Color.White,
                                            )
                                        ) {
                                            Icon(imageVector = if (currentLang == "fa") Icons.Default.ArrowForward else Icons.Default.ArrowBack , contentDescription = null )
                                            Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                            Text(
                                                text = stringResource(id = R.string.battery_charging),
                                                fontFamily = FontFamily(Font(R.font.ir_medium)),
                                                fontSize = 14.sp)
                                        }

                                        Spacer(modifier = Modifier.size(screenHeight * 0.017f))
                                    }
                                }

                                Spacer(modifier = Modifier.height(screenHeight * 0.017f))

                                Card(
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 6.dp,
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = White
                                    ),
                                    modifier = Modifier
                                        .width(screenWidth * 0.786f)
                                ){
                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .background(White, RoundedCornerShape(12.dp)),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f)
                                    ) {
                                        Spacer(modifier = Modifier.size(screenHeight * 0.001f))
                                        Row(
                                            Modifier
                                                .width(screenWidth * 0.405f),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_discount),
                                                contentDescription = "",
                                                modifier = Modifier.size(24.dp)
                                            )
                                            Text(text = stringResource(id = R.string.special_offers),
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = Gunmetal
                                            )
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_discount),
                                                contentDescription = "",
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }

                                        HorizontalDivider(
                                            modifier = Modifier.width(screenWidth * 0.652f),
                                            thickness = 1.dp,
                                            color = Gray04
                                        )


                                        Column (
                                            modifier = Modifier
                                                .width(screenWidth * 0.71f),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f)
                                        ){
                                            repeat(itemsOffers.size) { page ->
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.spacedBy(screenHeight * 0.002f)
                                                ) {
                                                    Row(
                                                        horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.01f),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.ic_battery),
                                                            contentDescription = "",
                                                            modifier = Modifier.size(24.dp)
                                                        )
                                                        Text(text = itemsOffers[page].firstCount.toString(),
                                                            style = MaterialTheme.typography.bodySmall.copy(
                                                                fontSize = 14.sp
                                                            ),
                                                            color = Gunmetal
                                                        )
                                                        Text(text = itemsOffers[page].firstText,
                                                            style = MaterialTheme.typography.bodyLarge,
                                                            color = Gunmetal
                                                        )

                                                    }

                                                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl){
                                                        Row(
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Row(
                                                                horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.005f,Alignment.End),
                                                                verticalAlignment = Alignment.CenterVertically,
                                                                modifier = Modifier
                                                                    .fillMaxWidth(0.333f)
                                                            ) {
                                                                Text(
                                                                    text = itemsOffers[page].nftCount.toString(),
                                                                    style = MaterialTheme.typography.bodySmall,
                                                                    color = PoliceBlue,
                                                                )
                                                                Text(
                                                                    text = "NFT",
                                                                    style = MaterialTheme.typography.bodySmall,
                                                                    color = PoliceBlue,
                                                                )
                                                                Spacer(modifier = Modifier.width(screenWidth * 0.087f))
                                                            }

                                                            Row(
                                                                horizontalArrangement = Arrangement.Start,
                                                                verticalAlignment = Alignment.CenterVertically,
                                                                modifier = Modifier
                                                                    .fillMaxWidth(0.7f)
                                                            ) {
                                                                Text(
                                                                    text = itemsOffers[page].smartContractCount.toString(),
                                                                    style = MaterialTheme.typography.bodySmall,
                                                                    color = PoliceBlue,
                                                                )

                                                                Spacer(modifier = Modifier.width(screenWidth * 0.005f))

                                                                Text(
                                                                    text = "Smart Contract",
                                                                    style = MaterialTheme.typography.bodySmall,
                                                                    color = PoliceBlue,
                                                                )
                                                            }

                                                            Row(
                                                                horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.005f,Alignment.End),
                                                                verticalAlignment = Alignment.CenterVertically,
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                            ) {
                                                                Text(
                                                                    text = itemsOffers[page].trxCount.toString(),
                                                                    style = MaterialTheme.typography.bodySmall,
                                                                    color = PoliceBlue,
                                                                )
                                                                Text(
                                                                    text = "TRX",
                                                                    style = MaterialTheme.typography.bodySmall,
                                                                    color = PoliceBlue,
                                                                )
                                                            }

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(screenHeight * 0.001f))
                                    }
                                }

                                Spacer(modifier = Modifier.size(screenHeight * 0.022f))
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.88f)
                                    .verticalScroll(scrollState)
                                    .border(1.dp, GrayC, RoundedCornerShape(16.dp)),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(screenHeight * 0.022f))

                                Row(
                                    Modifier
                                        .fillMaxWidth(0.89f), // 324
//                                    .height(60.dp),
                                    horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.04f)
                                ) {
                                    Card(
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 6.dp,
                                        ),
                                        colors = CardDefaults.cardColors(
                                            containerColor = White
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth(0.425f)
                                    ){
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(White, RoundedCornerShape(12.dp))
                                        ){
                                            Image(
                                                painter = painterResource(id = R.drawable.vc_tether_usdt_logo),
                                                contentDescription ="",
                                                modifier = Modifier
                                                    .size(27.dp)
                                                    .align(Alignment.CenterStart)
                                                    .offset(x = 18.dp)
                                            )
                                            Column(
                                                modifier = Modifier
                                                    .align(Alignment.CenterStart)
                                                    .offset(x = 49.dp),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                Text(text = stringResource(id = R.string.token),
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = GrayD
                                                )
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(text = if (currentLang == "fa") "تتر" else "USDT",
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = Gunmetal
                                                    )
                                                    Icon(
                                                        imageVector = if (currentLang == "fa") Icons.Default.KeyboardArrowLeft else Icons.Default.KeyboardArrowRight,
                                                        contentDescription = "",
                                                        tint = EerieBlack,
                                                        modifier = Modifier.size(16.dp)
                                                    )

                                                }
                                            }
                                        }
                                    }

                                    Card(
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 6.dp,
                                        ),
                                        colors = CardDefaults.cardColors(
                                            containerColor = White
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ){
                                        Box(modifier = Modifier
                                            .fillMaxWidth()
//                                    .height(60.dp)
                                            .background(White, RoundedCornerShape(12.dp))
                                        ){
                                            Image(
                                                painter = painterResource(id = R.drawable.vc_binance_coin_bnb_logo),
                                                contentDescription ="",
                                                modifier = Modifier
                                                    .size(27.dp)
                                                    .align(Alignment.CenterStart)
                                                    .offset(x = screenWidth * 0.019f)
                                            )
                                            Column(
                                                modifier = Modifier
                                                    .align(Alignment.CenterStart)
                                                    .offset(x = screenWidth * 0.119f),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                Text(text = stringResource(id = R.string.network),
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = GrayD
                                                )
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(text = if (currentLang == "fa") "بایننس (BNB)" else "Binance",
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = Gunmetal
                                                    )
                                                    Icon(
                                                        imageVector = if (currentLang == "fa") Icons.Default.KeyboardArrowLeft else Icons.Default.KeyboardArrowRight,
                                                        contentDescription = "",
                                                        tint = EerieBlack,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }

                                }

                                Spacer(modifier = Modifier.height(screenHeight * 0.017f))

                                Card(
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 6.dp,
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = White
                                    ),
                                    modifier = Modifier
                                        .width(screenWidth * 0.786f)
                                        .height(60.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(White, RoundedCornerShape(12.dp)),
                                         horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.04f)
                                    ) {
                                        Row(modifier = Modifier
                                            .width(screenWidth * 0.373f)
                                            .height(60.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start
                                        ){
                                            Icon(
                                                painter = painterResource(id = R.drawable.vc_user),
                                                contentDescription = "",
                                                tint = PoliceBlue,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                            Text(text = stringResource(id = R.string.token_from_account),
                                                style = MaterialTheme.typography.bodySmall,
                                                color = PoliceBlue
                                            )
                                            Spacer(modifier = Modifier.width(screenWidth * 0.04f))
                                        }

                                        Row(modifier = Modifier
                                            .width(screenWidth * 0.373f)
                                            .height(60.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ){
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_avatar),
                                                contentDescription ="",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(32.dp)
                                                    .clip(HexagonShape())
                                            )
                                            Text(text = if (currentLang == "fa") "ریحانه کشاورز حداد" else "Reyhaneh keshavarz",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Gunmetal
                                            )
                                            Icon(
                                                imageVector = if (currentLang == "fa") Icons.Default.KeyboardArrowLeft else Icons.Default.KeyboardArrowRight,
                                                contentDescription = "",
                                                tint = EerieBlack,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                    }
                                }


                                Spacer(modifier = Modifier.height(screenHeight * 0.026f))

                                Card(
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 6.dp,
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = White
                                    ),
                                    modifier = Modifier
                                        .width(screenWidth * 0.786f)
                                ){
                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .background(White, RoundedCornerShape(12.dp)),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(modifier = Modifier.height(screenHeight * 0.017f))
                                        Box(
                                            Modifier.width(screenWidth * 0.71f),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            Text(text = stringResource(id = R.string.token_amount),
                                                style = MaterialTheme.typography.bodySmall,
                                                color = PoliceBlue)
                                        }

                                        Spacer(modifier = Modifier.height(screenHeight * 0.004f))

                                        val amountTokenText = if (currentLang == "fa") convertEnglishDigitsToPersian(amountToken) else amountToken
                                        TextField(
                                            value = amountTokenText,
                                            onValueChange ={
                                                amountToken = it
                                            },
                                            textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center),
                                            leadingIcon = {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.vc_b_price) , contentDescription = "" ,
                                                    tint = NationsBlue,
                                                    modifier = Modifier
                                                        .size(24.dp)
                                                )
                                            },
                                            singleLine = true,
                                            shape = RoundedCornerShape(12.dp),
                                            colors =  TextFieldDefaults.colors(
                                                focusedContainerColor = NationsBlue.copy(alpha = 0.1f),
                                                unfocusedContainerColor = NationsBlue.copy(alpha = 0.1f),
                                                focusedTextColor = Gunmetal,
                                                unfocusedTextColor = Gunmetal,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent
                                            ),
                                            keyboardOptions = KeyboardOptions(
                                                autoCorrect = false,
                                                keyboardType = KeyboardType.Number
                                            ),
                                            modifier = Modifier
                                                .width(screenWidth * 0.71f)
                                                .height(48.dp)
                                                .border(
                                                    1.dp,
                                                    NationsBlue,
                                                    RoundedCornerShape(12.dp)
                                                )
                                        )

                                        Spacer(modifier = Modifier.height(screenHeight * 0.004f))
                                        Row(
                                            Modifier.width(screenWidth * 0.71f),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.End)
                                        ) {
                                            Text(text = stringResource(id = R.string.toman),
                                                style = MaterialTheme.typography.bodySmall,
                                                color = NationsBlue
                                            )

                                            val amountTomanText = formatWithDots("3350000",currentLang.toString()) // اضافه کردن نقطه بعد از 3 رقم
                                            Text(
                                                text = amountTomanText,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = NationsBlue
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(screenHeight * 0.009f))

                                        if (state == 0){ // انتقال توکن
                                            Box(
                                                modifier =  Modifier
                                                    .width(screenWidth * 0.71f),
                                                contentAlignment = Alignment.CenterStart
                                            ) {
                                                Text(text = stringResource(id = R.string.wallet_address),
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = PoliceBlue)
                                            }
                                            Spacer(modifier = Modifier.height(screenHeight * 0.004f))

                                            val walletAddressText = if (currentLang == "fa") convertEnglishDigitsToPersian(walletAddress) else walletAddress
                                            TextField(
                                                value = walletAddressText,
                                                onValueChange ={
                                                    walletAddress = it
                                                },
                                                textStyle = MaterialTheme.typography.titleMedium.copy(
                                                    textAlign = TextAlign.Center,
                                                ),
                                                leadingIcon = {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.vc_wallet) ,
                                                        contentDescription = "" ,
                                                        tint = NationsBlue,
                                                        modifier = Modifier
                                                            .size(24.dp)
                                                    )
                                                },
                                                singleLine = true,
                                                shape = RoundedCornerShape(12.dp),
                                                colors =  TextFieldDefaults.colors(
                                                    focusedContainerColor = NationsBlue.copy(alpha = 0.1f),
                                                    unfocusedContainerColor = NationsBlue.copy(alpha = 0.1f),
                                                    focusedTextColor = Gunmetal,
                                                    unfocusedTextColor = Gunmetal,
                                                    focusedIndicatorColor = Color.Transparent,
                                                    unfocusedIndicatorColor = Color.Transparent
                                                ),
                                                modifier = Modifier
                                                    .width(screenWidth * 0.71f)
                                                    .height(48.dp)
                                                    .border(
                                                        1.dp,
                                                        NationsBlue,
                                                        RoundedCornerShape(12.dp)
                                                    )
                                            )
                                            Spacer(modifier = Modifier.height(screenHeight * 0.009f))
                                        } else if (state == 2){ // فروش توکن
                                            Box(
                                                modifier = Modifier
                                                    .width(screenWidth * 0.71f),
                                                contentAlignment = Alignment.CenterStart
                                            ) {
                                                Text(text = stringResource(id = R.string.card_number_or_iban),
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = PoliceBlue)
                                            }
                                            Spacer(modifier = Modifier.height(screenHeight * 0.004f))

                                            TextField(
                                                value = cardNumber, onValueChange ={
                                                    cardNumber = it
                                                },
                                                textStyle = MaterialTheme.typography.titleMedium.copy(
                                                    textAlign = TextAlign.Center,
                                                ),
                                                leadingIcon = {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.vc_credit_card),
                                                        contentDescription = "",
                                                        tint = NationsBlue,
                                                        modifier = Modifier
                                                            .size(24.dp)
                                                    )
                                                },
                                                trailingIcon = {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.ic_mellat) ,
                                                        contentDescription = "" ,
                                                        modifier = Modifier
                                                            .size(24.dp)
                                                    )
                                                },
                                                singleLine = true,
                                                shape = RoundedCornerShape(12.dp),
                                                colors =  TextFieldDefaults.colors(
                                                    focusedContainerColor = NationsBlue.copy(alpha = 0.1f),
                                                    unfocusedContainerColor = NationsBlue.copy(alpha = 0.1f),
                                                    focusedTextColor = Gunmetal,
                                                    unfocusedTextColor = Gunmetal,
                                                    focusedIndicatorColor = Color.Transparent,
                                                    unfocusedIndicatorColor = Color.Transparent
                                                ),
                                                modifier = Modifier
                                                    .width(screenWidth * 0.71f)
                                                    .height(48.dp)
                                                    .border(
                                                        1.dp,
                                                        NationsBlue,
                                                        RoundedCornerShape(12.dp)
                                                    )
                                            )
                                            Spacer(modifier = Modifier.height(screenHeight * 0.009f))
                                        }

                                        Box(modifier = Modifier
                                            .width(screenWidth * 0.71f)
                                        ){
                                            RadioButton(selected = isBatriLowRadio,
                                                onClick = {
                                                    isCardLowRadio = false
                                                    isBatriLowRadio = true
                                                },
                                                modifier = Modifier.align(Alignment.CenterStart),
                                                colors =  RadioButtonDefaults. colors(
                                                    unselectedColor = GrayC,
                                                    selectedColor = NationsBlue
                                                )
                                            )

                                            Text(text = stringResource(id = R.string.deduct_from_battery),
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Gunmetal,
                                                modifier = Modifier
                                                    .align(Alignment.CenterStart)
                                                    .offset(x = screenWidth * 0.098f)
                                            )

                                            Row(
                                                modifier = Modifier
                                                    .width(screenWidth * 0.157f)
                                                    .height(26.dp)
                                                    .align(Alignment.CenterEnd)
                                                    .background(
                                                        Succes.copy(alpha = 0.1f),
                                                        RoundedCornerShape(6.dp)
                                                    ),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Text(text = stringResource(id = R.string.energy),
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = Succes
                                                )
                                                val energyText = if (currentLang == "fa") convertEnglishDigitsToPersian(" 80%") else " 80%"
                                                Text(text = energyText,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = Succes
                                                )
                                            }
                                        }

                                        Box(modifier = Modifier
                                            .width(screenWidth * 0.71f)
                                        ){
                                            RadioButton(selected = isCardLowRadio,
                                                onClick = {
                                                    isCardLowRadio = true
                                                    isBatriLowRadio = false
                                                },
                                                modifier = Modifier.align(Alignment.CenterStart),
                                                colors =  RadioButtonDefaults. colors(
                                                    unselectedColor = GrayC,
                                                    selectedColor = NationsBlue
                                                )
                                            )

                                            Text(text = stringResource(id = R.string.deduct_from_card),
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Gunmetal,
                                                modifier = Modifier
                                                    .align(Alignment.CenterStart)
                                                    .offset(x = screenWidth * 0.098f)
                                            )

                                            Row(
                                                modifier = Modifier
                                                    .align(Alignment.CenterEnd),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.End
                                            ) {
                                                Text(text = stringResource(id = R.string.transaction_fee),
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = PoliceBlue
                                                )
                                                val amountFeeText = if (currentLang == "fa") convertEnglishDigitsToPersian("0") else "0"
                                                Text(text = amountFeeText,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = PoliceBlue
                                                )
                                                Text(text = " avsin",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = PoliceBlue
                                                )
                                            }
                                        }


                                        Spacer(modifier = Modifier.height(screenHeight * 0.017f))

                                        Button(
                                            onClick = {
                                                when(state){
                                                    0 -> isDialogTransactionSuccess = true
                                                    1 -> isDialogBuySuccess = true
                                                    2 -> isDialogSellSuccess = true
                                                }
                                            },
                                            modifier = Modifier
                                                .align(Alignment.CenterHorizontally)
                                                .width(screenWidth * 0.71f)
                                                .height(48.dp),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = ButtonDefaults. buttonColors(
                                                containerColor = NationsBlue,
                                                contentColor = Color.White,
                                            )
                                        ) {
                                            Icon(
                                                imageVector = if (currentLang == "fa") Icons.Default.ArrowForward else Icons.Default.ArrowBack , contentDescription = null )
                                            Spacer(modifier = Modifier.width(screenWidth * 0.04f))
                                            Text(
                                                text = if (state == 0) { stringResource(id = R.string.token_transfer)
                                                } else if (state == 1) {
                                                    stringResource(id = R.string.token_buy)
                                                } else {
                                                    stringResource(id = R.string.token_sell)
                                                },
                                                fontFamily = FontFamily(Font(R.font.ir_medium)),
                                                fontSize = 14.sp)
                                            Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                        }
                                        Spacer(modifier = Modifier.size(screenHeight * 0.017f))
                                    }
                                }
                                Spacer(modifier = Modifier.size(screenHeight * 0.022f))
                            }

                        }
                    }

                    Spacer(modifier = Modifier.size(screenHeight * 0.017f))
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
fun ExchangePreview() {
    SinarTheme {
        ExchangeScreen()
    }
}
