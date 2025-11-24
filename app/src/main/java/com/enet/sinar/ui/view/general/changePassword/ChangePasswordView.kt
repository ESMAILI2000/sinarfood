package com.enet.sinar.ui.view.general.changePassword

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.enet.sinar.ui.theme.Succes
import com.enet.sinar.ui.utility.MySharedPreferences
import com.enet.sinar.ui.view.convertEnglishDigitsToPersian
import com.enet.sinar.ui.view.convertPersianDigitsToEnglish
import com.enet.sinar.ui.view.custom_view.BoxedTextInput
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {},
    onNavigateToBack: () -> Unit = {},
) {

    // برای رفتن به تکست فیلد بعدی بعد از پر کردن تکست فیلد
    val focusManager = LocalFocusManager.current
    val focusPassword = remember { FocusRequester() }
    val focusRePassword = remember { FocusRequester() }

    var otpCode by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rePassword by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf(false) }
    var time by rememberSaveable { mutableStateOf(30) }
    var step by rememberSaveable { mutableStateOf(1) } // 1 -> وارد کردن شماره تلفن 2-> وارد کردن کد 3-> وارد کردن رمز جدید

    var isShowPassword by remember { mutableStateOf(false) }
    var isShowRepassword by remember { mutableStateOf(false) }

    var isSuccessOtp by remember { mutableStateOf(false) }
    var enableButton by remember { mutableStateOf(true) }
    val context = LocalContext.current

    if (otpCode.length == 5){
        otpCode = convertPersianDigitsToEnglish(otpCode)
        if (otpCode == "11111"){
            isSuccessOtp = true
            error = false
            time = 0
            enableButton = true
            step = 3
        }else{
            isSuccessOtp = false
            error = true
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

    val layoutDirection = if (currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        BoxWithConstraints (
            modifier = Modifier
                .background(Background)
                .padding(
                    bottom = WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding() + 1.dp
                )
                .fillMaxSize()
        ) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight

            Icon(
                imageVector = if (currentLang == "fa") Icons.Default.ArrowBack else Icons.Default.ArrowForward ,
                contentDescription = null,
                tint = Gunmetal,
                modifier = Modifier
                    .padding(horizontal = screenWidth * 0.06f, vertical = screenHeight * 0.07f)
                    .align(Alignment.TopEnd)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            onNavigateToBack()
                        }
                    }

            )

            Column(
                Modifier
//                    .width(364.dp)
                    .fillMaxWidth(0.88f)
                    .align(Alignment.Center)
//                .padding(bottom = 0.dp, top =  290.dp)
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
                    text = stringResource(
                        id = R.string.password_recovery
                    ),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = screenHeight * 0.008f),
                    fontSize = 18.sp,
                    color = Gunmetal
                )
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Text(
                            text = if (step == 3 && currentLang =="fa")"تبریک! گذرواژه جدید خود را وارد کنید" else stringResource(
                                id = R.string.please_fill_information
                            ),
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
                        .align(Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    val phoneNumberText = if (currentLang == "fa") convertEnglishDigitsToPersian(phoneNumber) else phoneNumber
                    OutlinedTextField(
                        value = phoneNumberText,
                        onValueChange = {
                            if (it.length <= 11) {
                                phoneNumber = it
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = NationsBlue,
                            unfocusedBorderColor = GrayC,
                            focusedLabelColor = NationsBlue,
                            unfocusedLabelColor = GrayC,
                            focusedLeadingIconColor = NationsBlue,
                            unfocusedLeadingIconColor = GrayC,
                            focusedTextColor = NationsBlue
                        ),
                        placeholder = {
                            Text(stringResource(
                                id = R.string.phone_number
                            ),
                                fontFamily = FontFamily(Font(R.font.ir_medium)),
                                color = GrayC,
                                fontSize = 12.sp)
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.vc_phone_call),
                                contentDescription = null,
//                                tint = GrayC
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Phone ,
                            imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus()  }
                        )
                    )

//                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        AnimatedVisibility(
                            visible = if (step != 1) true else false,
                        ){
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                                val otpCodeText = if (currentLang == "fa") convertEnglishDigitsToPersian(otpCode) else otpCode
                                BoxedTextInput(
                                    value = otpCodeText,
                                    length = 5,
                                    onValueChange = {
                                            otpCode = it
                                                    },
                                    modifier = Modifier
                                        .padding(top = screenHeight * 0.008f)
                                        .align(Alignment.CenterHorizontally),
                                    textBorderShape = RoundedCornerShape(12.dp),
                                    textArrangement = Arrangement.spacedBy(4.dp),
                                    textStyle = TextStyle(fontSize = 18.sp, color = if (error) Err else if (isSuccessOtp) Succes else NationsBlue),
                                    emptyTextBorderColor = GrayC,
                                    filledTextBorderColor = if (error) Err else if (isSuccessOtp) Succes else NationsBlue,
                                    backgroundColor = if (error) Color.Transparent else if (isSuccessOtp) Color.Transparent else Background ,
                                    keyboardOptions = KeyboardOptions(
                                        autoCorrect = false,
                                        keyboardType = KeyboardType.NumberPassword
                                    ),
                                    with = screenWidth * 0.15f,
                                    height = screenHeight * 0.06f
                                )
                            }

                        }

                        AnimatedVisibility(
                            visible = if (error) true else false,
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = screenHeight * 0.008f),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = stringResource(id = R.string.code_input_error),
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(start = screenWidth * 0.01f),
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font( R.font.ir_regular)),
                                    color = Err,
                                    maxLines = 1,
                                    textAlign = TextAlign.Start,
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
                            visible = if (enableButton && step != 2) false else true,
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
                                    )
                                }
                            }
                        }

                    AnimatedVisibility(
                        visible = if (step == 3) true else false,
                    ){
                        Column {
                            OutlinedTextField(
                                value = password,
                                onValueChange = {
                                    if (it.length <= 50) {
                                        password = it
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusPassword),
                                label = {
                                    Text( stringResource(id = R.string.new_password),
                                    fontFamily = FontFamily(Font(R.font.ir_medium)),
                                    fontSize = 12.sp)
                                        },
                                singleLine = true,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedTextColor = NationsBlue,
                                    unfocusedTextColor = GrayC,
                                    focusedBorderColor = NationsBlue,
                                    unfocusedBorderColor = GrayC,
                                    focusedLabelColor = NationsBlue,
                                    unfocusedLabelColor = GrayC,
                                    focusedTrailingIconColor = NationsBlue,
                                    unfocusedTrailingIconColor = GrayC,
                                    focusedLeadingIconColor = NationsBlue,
                                    unfocusedLeadingIconColor = GrayC,
                                    cursorColor = GrayC
                                ),
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.vc_lock_open),
                                        contentDescription = null
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = if (isShowPassword) R.drawable.vc_eye else R.drawable.vc_eye_closed ),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .pointerInput(Unit){
                                                detectTapGestures {
                                                    isShowPassword = !isShowPassword
                                                }
                                            }
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusRePassword.requestFocus() }
                                ),
                                visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
                                )

                            OutlinedTextField(
                                value = rePassword,
                                onValueChange = {
                                    if (it.length <= 50) {
                                        rePassword = it
                                    }
                                },
                               modifier = Modifier
                                    .fillMaxWidth()
                                   .focusRequester(focusRePassword),
                                label = {
                                    Text(stringResource(id = R.string.confirm_new_password),
                                    fontFamily = FontFamily(Font(R.font.ir_medium)),
                                    fontSize = 12.sp)
                                        },
                                singleLine = true,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedTextColor = NationsBlue,
                                    unfocusedTextColor = GrayC,
                                    focusedBorderColor = NationsBlue,
                                    unfocusedBorderColor = GrayC,
                                    focusedLabelColor = NationsBlue,
                                    unfocusedLabelColor = GrayC,
                                    focusedTrailingIconColor = NationsBlue,
                                    unfocusedTrailingIconColor = GrayC,
                                    focusedLeadingIconColor = NationsBlue,
                                    unfocusedLeadingIconColor = GrayC,
                                    cursorColor = GrayC
                                ),
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.vc_lock_open),
                                        contentDescription = null
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = if (isShowRepassword) R.drawable.vc_eye else R.drawable.vc_eye_closed ),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .pointerInput(Unit){
                                                detectTapGestures {
                                                    isShowRepassword = !isShowRepassword
                                                }
                                            }
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(
                                    onDone = { focusManager.clearFocus() }
                                ),
                                visualTransformation = if (isShowRepassword) VisualTransformation.None else  PasswordVisualTransformation(),
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(screenHeight * 0.013f))
                    Button(
                        onClick = {
                            if (step == 1){
                                step = 2
                                enableButton = false
                            } else if (step == 2){
//                        error = !error
                                // وقتی ستاپ 3 میشود که کد را درست وارد کند
                                time = 30
                            } else {
                                onNavigateToHome()
                            }
                        },
                        Modifier
                            .fillMaxWidth()
                            .padding(top = screenHeight * 0.008f)
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
                        val iconButton = if (step == 1) {
                            if (currentLang == "fa") Icons.Default.ArrowForward else Icons.Default.ArrowBack
                        } else {Icons.Default.Done}

                        AnimatedVisibility(
                            visible = if (step != 2 ) true else false
                        ){
                            Icon(imageVector = iconButton , contentDescription = null )
                        }
                        AnimatedVisibility(
                            visible = if (step == 2 ) true else false
                        ){
                            Icon(painter = painterResource(R.drawable.vc_refresh) , contentDescription = null)
                        }

                        Spacer(modifier = Modifier.width(screenWidth * 0.02f))

                        Text( if (step == 1) stringResource(
                            id = R.string.get_code
                        ) else if (step == 2)stringResource(
                            id = R.string.resend
                        ) else  stringResource(
                            id = R.string.change_password
                        ),
                            fontFamily = FontFamily(Font(R.font.ir_medium)),
                            fontSize = 14.sp)
                    }

                }


            }


            Text(
                text = stringResource(id = R.string.app_version),
                Modifier
//                    .padding(bottom = 54.dp, end = 0.dp)
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
fun ChangePasswordPreview() {
    SinarTheme {
        ChangePasswordScreen()
    }
}