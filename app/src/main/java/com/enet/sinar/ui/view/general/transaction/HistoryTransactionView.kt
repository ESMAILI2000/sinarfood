package com.enet.sinar.ui.view.general.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enet.sinar.R
import com.enet.sinar.ui.theme.Background
import com.enet.sinar.ui.theme.Err
import com.enet.sinar.ui.theme.GrayB
import com.enet.sinar.ui.theme.GrayD
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTransactionScreen(
    modifier: Modifier = Modifier,
    onNavigateToBack: () -> Unit = {},
){

    val context = LocalContext.current
    var transactionId by remember { mutableStateOf("722535") }
    var amount by remember { mutableStateOf("5.76") }
    var transactionDate by remember { mutableStateOf("1404/07/02") }
    var transactionTime by remember { mutableStateOf("12:30:24") }
    var walletAddress by remember { mutableStateOf("0x5A141B7eba38A151EDfcd816D912E6ae5C0307b1") }



    val currentLang = MySharedPreferences.getLang(context)

    var transactionCause by remember { mutableStateOf( if (currentLang == "fa")"خرید از رستوران" else "Purchase from restaurant") }

    val itemsHistory = if (currentLang == "fa") listOf(
            Pair("سبحان کشاورز", 1), // 0 -> در انتظار تایید  1-> تایید شده  2-> رد شده
            Pair("علی محمدی", 2),
            Pair("علی محمدی", 0),
            Pair("حسین محمدی", 0),
            Pair("متین عروتی", 1),
    ) else listOf(
            Pair("Sobhan Keshavarz", 1),
            Pair("Ali Mohammadi", 2),
            Pair("Ali Mohammadi", 0),
            Pair("Hoseyn Mohammadi", 0),
            Pair("Matin Orvati", 1),
    )
    val layoutDirection = if (currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {

        BoxWithConstraints (
            modifier = Modifier
        ) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight

            Column(modifier = Modifier
                .fillMaxSize()
                .background(Background)
            ) {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = R.string.invoice),
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

                LazyColumn(
                    modifier = Modifier
                        .padding(top = 1.dp)
                        .fillMaxSize()
                        .background(White, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
                    contentPadding = PaddingValues(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f)
                ) {
                    items(itemsHistory.size) { page ->
                        DashedContainer(
                            modifier = Modifier
                                .width(screenWidth * 0.883f)
                                .background(White, shape = RoundedCornerShape(24.dp)),
                            cornerRadius = 24.dp,
                            borderColor = Water,
                            strokeWidth = 1.dp,
                            dashLength = 4.dp,
                            gapLength = 5.dp
                        ) {
                            Column(
                                Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(screenHeight * 0.09f)
                                        .background(
                                            Color(0xFF539DF3).copy(0.08f),
                                            RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                                        ),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                        val trackingCode = stringResource(id = R.string.label_tracking_code)
                                        val transactionIdText = if (currentLang == "fa") convertEnglishDigitsToPersian(transactionId) else transactionId
                                        Text(
                                            text = " $trackingCode $transactionIdText",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = NationsBlue
                                        )

                                    Row(
                                        Modifier
                                            .fillMaxWidth(0.95f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            Modifier
                                                .fillMaxWidth(0.7f),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                painter = painterResource(id = when(itemsHistory[page].second){
                                                2-> R.drawable.vc_circle_chevron_up
                                                else -> {
                                                    R.drawable.vc_circle_chevron_down
                                                }
                                            }
                                                ),
                                                contentDescription = "",
                                                tint = if (itemsHistory[page].second != 2) Succes else Err,
                                                modifier =  Modifier.size(18.dp)
                                            )

                                            Spacer(modifier = Modifier.width(screenWidth * 0.01f))

                                            val deposit = stringResource(id = R.string.label_deposit_amount)
                                            val sinar = stringResource(id = R.string.app_name)
                                            val from = stringResource(id = R.string.label_from_address)
                                            val amountText = if (currentLang == "fa") convertEnglishDigitsToPersian(amount) else amount
                                            Text(
                                                text = "$deposit $amountText $sinar $from",
                                                maxLines = 1,
                                                color = Gunmetal,
                                                style = MaterialTheme.typography.bodyLarge,
                                                overflow = TextOverflow.Visible,
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth(0.9f))
                                        }
                                        val walletAddressText = if (currentLang == "fa") convertEnglishDigitsToPersian(walletAddress) else walletAddress
                                        EllipsizedMiddleText(
                                            text = walletAddressText,
                                            startLength = 7,
                                            endLength = 5,
                                            maxLines = 1,
                                            color = PoliceBlue,
                                            style = MaterialTheme.typography.bodyLarge,
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        )

                                        Spacer(modifier = Modifier.width(screenWidth * 0.01f))
                                    }

                                    Spacer(modifier = Modifier.height(screenHeight * 0.008f))
                                }

                                Column(
                                    Modifier
                                        .fillMaxWidth(0.92f)
//                                        .height(screenHeight * 0.1f)
                                        .padding(top = screenHeight * 0.017f),
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Row(
                                        Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            Modifier
                                                .fillMaxWidth(0.6f),
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(painter = painterResource(id = R.drawable.vc_credit_card),
                                                contentDescription = "",
                                                tint = Gunmetal,
                                                modifier =  Modifier.size(18.dp))

                                            Text(text = stringResource(id = R.string.label_registered_domain),
                                                maxLines = 1,
                                                color = PoliceBlue,
                                                style = MaterialTheme.typography.bodySmall,
                                                overflow = TextOverflow.Visible,
                                                textAlign = TextAlign.End,
                                                modifier = Modifier
                                                    .padding(start = 4.dp))
                                        }
                                        Text(text = itemsHistory[page].first,
                                            maxLines = 1,
                                            color = Gunmetal,
                                            style = MaterialTheme.typography.bodySmall,
                                            overflow = TextOverflow.Visible,
                                            textAlign = TextAlign.End,
                                            modifier = Modifier
//                                                .padding(start = 8.dp)
                                                .fillMaxWidth())
                                    }
                                    Row(
                                        Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            Modifier
                                                .fillMaxWidth(0.6f),
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(painter = painterResource(id = R.drawable.vc_credit_card),
                                                contentDescription = "",
                                                tint = Gunmetal,
                                                modifier =  Modifier.size(18.dp))

                                            Text(text = stringResource(id = R.string.label_payment_reason),
                                                maxLines = 1,
                                                color = PoliceBlue,
                                                style = MaterialTheme.typography.bodySmall,
                                                overflow = TextOverflow.Visible,
                                                textAlign = TextAlign.End,
                                                modifier = Modifier
                                                    .padding(start = 4.dp))

                                        }
                                        Text(text = transactionCause,
                                            maxLines = 1,
                                            color = Gunmetal,
                                            style = MaterialTheme.typography.bodySmall,
                                            overflow = TextOverflow.Visible,
                                            textAlign = TextAlign.End,
                                            modifier = Modifier
//                                                .padding(end = 8.dp)
                                                .fillMaxWidth())
                                    }
                                    Row(
                                        Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            Modifier
                                                .fillMaxWidth(0.6f),
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(painter = painterResource(id = R.drawable.vc_calendar_due),
                                                contentDescription = "",
                                                tint = Gunmetal,
                                                modifier =  Modifier.size(18.dp))
                                            Text(text = stringResource(id = R.string.label_deposit_date),
                                                maxLines = 1,
                                                color = PoliceBlue,
                                                style = MaterialTheme.typography.bodySmall,
                                                overflow = TextOverflow.Visible,
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier
                                                    .padding(start = 4.dp))
                                        }

                                        val transactionDateText = if (currentLang == "fa") convertEnglishDigitsToPersian(transactionDate) else transactionDate
                                        Text(
                                            text = transactionDateText,
                                            maxLines = 1,
                                            color = Gunmetal,
                                            style = MaterialTheme.typography.bodySmall,
                                            overflow = TextOverflow.Visible,
                                            textAlign = TextAlign.End,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        )
                                    }
                                    Row(
                                        Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            Modifier
                                                .fillMaxWidth(0.6f),
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(painter = painterResource(id = R.drawable.vc_clock_hour),
                                                contentDescription = "",
                                                tint = Gunmetal,
                                                modifier =  Modifier.size(18.dp))

                                            Text(text = stringResource(id = R.string.label_deposit_time),
                                                maxLines = 1,
                                                color = PoliceBlue,
                                                style = MaterialTheme.typography.bodySmall,
                                                overflow = TextOverflow.Visible,
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier
                                                    .padding(start = 4.dp))
                                        }
                                        val transactionTimeText = if (currentLang == "fa") convertEnglishDigitsToPersian(transactionTime) else transactionTime
                                        Text(text = transactionTimeText,
                                            maxLines = 1,
                                            color = Gunmetal,
                                            style = MaterialTheme.typography.bodySmall,
                                            overflow = TextOverflow.Visible,
                                            textAlign = TextAlign.End,
                                            modifier = Modifier
                                                .fillMaxWidth())
                                    }
                                }

                                HorizontalDivider(modifier = Modifier
                                    .fillMaxWidth(0.92f)
//                                    .width(332.dp)
                                    .padding(top = screenHeight * 0.017f),
                                    thickness = 1.dp,
                                    color = GrayB)

                                Row(
                                    Modifier
                                        .fillMaxWidth(0.92f)
                                        .padding(top = screenHeight * 0.017f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        Modifier
                                            .fillMaxWidth(0.6f),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(painter = painterResource(id = R.drawable.vc_progress_down),
                                            contentDescription = "",
                                            tint = Gunmetal,
                                            modifier =  Modifier.size(24.dp))

                                        Text(text = stringResource(id = R.string.label_receipt_status),
                                            maxLines = 1,
                                            color = Gunmetal,
                                            style = MaterialTheme.typography.bodyLarge,
                                            overflow = TextOverflow.Visible,
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier
                                                .padding(start = 8.dp))
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
//                                            .padding(start = 8.dp),
                                        horizontalArrangement = Arrangement.End

                                    ){
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
//                                                .width(114.dp)
                                                .height(screenHeight * 0.039f)
                                                .border(
                                                    1.dp,
                                                    when (itemsHistory[page].second) {
                                                        0 -> GrayD
                                                        1 -> Succes
                                                        else -> {
                                                            Color(0xFFD84040)
                                                        }
                                                    },
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .background(
                                                    when (itemsHistory[page].second) {
                                                        0 -> White
                                                        1 -> Succes.copy(alpha = 0.16f)
                                                        else -> {
                                                            Color(0xFFD84040).copy(alpha = 0.16f)
                                                        }
                                                    },
                                                    RoundedCornerShape(8.dp)
                                                ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            if (itemsHistory[page].second != 0){
                                                Icon(
                                                    imageVector =  when(itemsHistory[page].second){
                                                        1-> Icons.Default.Check
                                                        else -> {
                                                            Icons.Default.Close
                                                        }
                                                    },
                                                    contentDescription ="" ,
                                                    tint = when(itemsHistory[page].second){
                                                        0-> GrayD
                                                        1-> Succes
                                                        else -> {
                                                            Color(0xFFD84040)
                                                        }
                                                    },
                                                    modifier = Modifier
                                                        .size(16.dp)
                                                        .padding(end = 2.dp))
                                            }

                                            Text(text = when(itemsHistory[page].second){
                                                0-> stringResource(id = R.string.status_pending)
                                                1-> stringResource(id = R.string.status_confirmed)
                                                else -> {
                                                    stringResource(id = R.string.status_rejected)
                                                }
                                            },
                                                style = MaterialTheme.typography.bodySmall,
                                                color = when(itemsHistory[page].second){
                                                    0-> GrayD
                                                    1-> Succes
                                                    else -> {
                                                        Color(0xFFD84040)
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(screenHeight * 0.017f))
                            }
                        }
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
fun HistoryTransactionPreview() {
    SinarTheme {
        HistoryTransactionScreen()
    }
}