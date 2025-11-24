package com.enet.sinar.ui.view.general.otp

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enet.sinar.R
import com.enet.sinar.ui.theme.Background
import com.enet.sinar.ui.theme.Err
import com.enet.sinar.ui.theme.GrayA
import com.enet.sinar.ui.theme.GrayC
import com.enet.sinar.ui.theme.GrayE
import com.enet.sinar.ui.theme.GrayF
import com.enet.sinar.ui.theme.Gunmetal
import com.enet.sinar.ui.theme.NationsBlue
import com.enet.sinar.ui.theme.SinarTheme
import com.enet.sinar.ui.utility.MySharedPreferences
import com.enet.sinar.ui.view.convertEnglishDigitsToPersian
import com.enet.sinar.ui.view.convertPersianDigitsToEnglish
import com.enet.sinar.ui.view.custom_view.BoxedTextInput
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {},
    onNavigateToBack: () -> Unit = {},
) {

    val context = LocalContext.current

    var otpCode by remember { mutableStateOf("")}
    var phoneNumber by remember { mutableStateOf("09227049474") }
    var error by remember { mutableStateOf(false) }
    var time by remember { mutableStateOf(30) }
    var enableButton by remember { mutableStateOf(false) }


    if (otpCode.length > 4){
        otpCode = convertPersianDigitsToEnglish(otpCode)
        if (otpCode == "11111"){
            onNavigateToHome()
        }
    }
    if (enableButton == false){
        LaunchedEffect(Unit) {
            for (i in 30 downTo 0) {
                delay(1000) // update once a second
                time = i
            }
            enableButton = true
        }
    }

    val currentLang = MySharedPreferences.getLang(context)

    // تبدیل اعداد انگلیسی شماره تلفن به فارسی
    if (currentLang == "fa"){
        phoneNumber = convertEnglishDigitsToPersian(phoneNumber)
    }else{

    }

    val layoutDirection = if (currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        BoxWithConstraints (
            modifier = Modifier
                .background(Background)
                .fillMaxSize()
        ) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight

            Icon(
                imageVector = if (currentLang == "fa") Icons.Default.ArrowBack else Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Gunmetal,
                modifier = Modifier
                    .padding(horizontal = screenWidth * 0.06f, vertical = screenHeight * 0.07f)
                    .align(Alignment.TopEnd)
                    .pointerInput(Unit){
                        detectTapGestures {
                            onNavigateToBack()
                        }
                    }

            )

            Column(
                Modifier
                    .fillMaxWidth(0.88f)
                    .align(Alignment.TopCenter)
                    .padding(top = screenHeight * 0.27f)
                    .clip(RoundedCornerShape(32.dp))
                    .background(color = Color.White)
            ) {
                val image = painterResource(R.drawable.logo)
                Image(
                    painter = image, contentDescription = "logo",
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(72.dp, 72.dp)
                        .padding(top = screenHeight * 0.026f)
                )
                Text(
                    text = stringResource(id = R.string.authentication),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = screenHeight * 0.008f),
                    color = Gunmetal
                )

                Column {
                    if (currentLang != "fa"){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Text(text = "Please enter the", style = MaterialTheme.typography.bodySmall,color = GrayF)
                            Text(text = " 5-digit ", style = MaterialTheme.typography.bodySmall,color = NationsBlue)
                        }
                        Text(
                            text = "code sent to the number",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = GrayF,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = phoneNumber,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = screenWidth * 0.02f),
                            style = MaterialTheme.typography.bodySmall,
                            color = NationsBlue,
                            textAlign = TextAlign.Center
                        )
                    }else{
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Text(text = "کد ", style = MaterialTheme.typography.labelSmall,color = GrayF)
                            Text(text = " ۵ ", style = MaterialTheme.typography.labelSmall,color = NationsBlue)
                            Text(text = "رقمی ارسال شده به شماره", style = MaterialTheme.typography.labelSmall,color = GrayF)

                        }
                        Text(
                            text = phoneNumber,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = screenWidth * 0.02f),
                            style = MaterialTheme.typography.labelSmall,
                            color = NationsBlue,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "را وارد کنید",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = screenWidth * 0.02f),
                            style = MaterialTheme.typography.labelSmall,
                            color = GrayF,
                            textAlign = TextAlign.Center
                        )
                    }
                }


                Column(
                    Modifier
                        .width(screenWidth * 0.8f)
                        .padding(vertical = screenHeight * 0.02f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                        val otpCodeText = if (currentLang == "fa") convertEnglishDigitsToPersian(otpCode) else otpCode
                        BoxedTextInput(
                            value = otpCodeText,
                            length = 5,
                            onValueChange = { otpCode = it },
                            modifier = Modifier
                                .padding(top = screenHeight * 0.008f)
                                .align(Alignment.CenterHorizontally),
                            textBorderShape = RoundedCornerShape(12.dp),
                            textArrangement = Arrangement.spacedBy(4.dp),
                            textStyle = TextStyle(fontSize = 18.sp, color = if (error) Err else NationsBlue),
                            emptyTextBorderColor = GrayC,
                            filledTextBorderColor = if (error) Err else NationsBlue,
                            backgroundColor = if (error) Color.Transparent else Background ,
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = false,
                                keyboardType = KeyboardType.NumberPassword
                            ),
                            with = screenWidth * 0.15f,
                            height = screenHeight * 0.06f
                        )
                    }
                    AnimatedVisibility(
                        visible = if (error) true else false,
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = screenHeight * 0.008f),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = stringResource(id = R.string.code_input_error),
                                Modifier
                                    .fillMaxWidth(0.9f)
                                    .padding(end = screenWidth * 0.01f),
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font( R.font.ir_regular)),
                                color = Err,
                                maxLines = 1,
                                textAlign = TextAlign.End,
                                overflow = TextOverflow.Clip,
//                        fontWeight = 500
                            )
                            Icon(painter = painterResource(id = R.drawable.vc_mood_sad) ,
                                contentDescription = null,
                                tint = Err
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = if (error) false else true,
                    ){
                        Row(
                            Modifier
                                .fillMaxWidth(1f)
                                .padding(top = screenHeight * 0.013f)
                        ) {
                            Box(modifier = Modifier.fillMaxWidth(0.5f)){
                                Text(
                                    text =  stringResource(
                                        id = R.string.resend_after
                                    ),
                                    Modifier.align(Alignment.CenterStart),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Gunmetal,
                                    textAlign = TextAlign.Start,
                                    maxLines = 1,
                                    overflow = TextOverflow.Clip
                                )
                            }
                            Box(modifier = Modifier.fillMaxWidth()){
                                Text(
                                    text = if (currentLang == "fa"){
                                        if (time>9) " ۰۰:${convertEnglishDigitsToPersian(time.toString())} " else " ۰۰:۰${convertEnglishDigitsToPersian(time.toString())}"
                                    }else{
                                        if (time>9) " 00:$time " else " 00:0$time"
                                         },
                                    modifier = Modifier.align(Alignment.TopEnd),
                                    style = MaterialTheme.typography.labelMedium.copy(color = Gunmetal),
                                    textAlign = TextAlign.End,
//                                color = NationsBlue
                                )

                            }
                        }
                    }



                    Spacer(modifier = Modifier.height(screenHeight * 0.017f))
                    Button(
                        onClick = {
                            enableButton = false
//                        error = !error
                            time = 30},
                        Modifier
                            .fillMaxWidth()
                            .size(0.dp, 50.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = enableButton,
                        colors = ButtonColors(
                            containerColor = NationsBlue,
                            contentColor = Color.White,
                            disabledContentColor = GrayE,
                            disabledContainerColor = GrayA
                        )
                    ) {
                        Text(
                            stringResource(id = R.string.resend),
                            fontFamily = FontFamily(Font(R.font.ir_medium)),
                            fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                        Icon(painter = painterResource(R.drawable.vc_refresh) , contentDescription = null )
                    }

                }


            }


            Text(
                text = stringResource(id = R.string.app_version),
                Modifier
//                .padding(bottom = 54.dp, end = 0.dp)
                    .align(Alignment.BottomCenter),
                color = Gunmetal,
                fontFamily = FontFamily(Font(R.font.ir_medium)),
                fontSize = 14.sp
            )
        }
    }


}


@Preview(
    showBackground = true,
    apiLevel = 26,
    device = "spec:width=412dp,height=917dp,dpi=480"
)
@Composable
fun OtpPreview() {
    SinarTheme {
        OtpScreen()
    }
}