package com.enet.sinar.ui.view.general.login

import AppSection
import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.enet.sinar.ui.theme.Blue200
import com.enet.sinar.ui.theme.Err
import com.enet.sinar.ui.theme.GrayA
import com.enet.sinar.ui.theme.GrayC
import com.enet.sinar.ui.theme.GrayE
import com.enet.sinar.ui.theme.GrayF
import com.enet.sinar.ui.theme.Gunmetal
import com.enet.sinar.ui.theme.NationsBlue
import com.enet.sinar.ui.theme.Primary
import com.enet.sinar.ui.theme.SinarTheme
import com.enet.sinar.ui.theme.White
import com.enet.sinar.ui.utility.MySharedPreferences.getLang
import com.enet.sinar.ui.utility.MySharedPreferences.getisLogin
import com.enet.sinar.ui.utility.MySharedPreferences.setUserType
import com.enet.sinar.ui.utility.MySharedPreferences.setisLogin
import com.enet.sinar.ui.utility.MySharedPreferences.setlang
import com.enet.sinar.ui.view.convertEnglishDigitsToPersian


@SuppressLint("UnusedBoxWithConstraintsScope", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginStudent: () -> Unit = {},
    onLoginHome: () -> Unit = {},
    onRecoveryPassword: () -> Unit = {},
    onAuthentication: () -> Unit = {},
) {

    // برای تغییر زبان
    val context = LocalContext.current
    val activity = context as? Activity
    val currentLang = getLang(context)

    val scrollState = rememberScrollState() // برای اسکرول شدن در صفحه نمایش های کوچیک
    var captchaText by remember { mutableStateOf(generateCaptcha()) }

    var userId by remember { mutableStateOf("") } // شماره دانشجویی یا کد پرسنلی
    var password by remember { mutableStateOf("") }
    var captchaInput by remember { mutableStateOf("") }

    val maxLengtUserId = 20
    val maxLengtPassword = 50
    val maxLengtCaptcha = 6

    var errorUserId by remember { mutableStateOf(false) }
    var errorPassword by remember { mutableStateOf(false) }
    var errorCapcha by remember { mutableStateOf(false) }
    var isPasswordHide by remember { mutableStateOf(true) }  // پنهان بودن رمز

    //
    var enableLoginAcademic by remember { mutableStateOf(false) }

    // برای رفتن به تکست فیلد بعدی بعد از پر کردن تکست فیلد
    val focusManager = LocalFocusManager.current
    val focusUserId = remember { FocusRequester() }
    val focusPassword = remember { FocusRequester() }
    val focusCapcha = remember { FocusRequester() }

    val layoutDirection = if (currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        BoxWithConstraints (
            modifier = Modifier
                .background(Background)
                .fillMaxSize()
        ) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight

            Column(
                Modifier
                    .fillMaxWidth(0.88f)
                    .align(Alignment.TopCenter)
                    .padding(
                        top = screenHeight * 0.13f,
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding() + screenHeight * 0.008f
                    )
                    .clip(RoundedCornerShape(32.dp))
                    .background(color = Color.White)
            ) {
                val image = painterResource(R.drawable.logo)
                    Image(
                        painter = image, contentDescription = "logo",
                        Modifier
                            .align(Alignment.CenterHorizontally)
//                        .size(72.dp, 72.dp)
                            .fillMaxWidth(0.17f)
//                        .fillMaxHeight(0.078f)
                            .padding(top = screenHeight * 0.026f)
                    )
                    Text(
                        text = stringResource(id = R.string.login),
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = screenHeight * 0.008f),
                        fontSize = 18.sp,
                        color = Gunmetal
                    )
                    Text(
                        text = stringResource(id = R.string.enter_your_information_accurately),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = screenWidth * 0.02f),
                        color = GrayF,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        fontFamily = FontFamily(Font(R.font.ir_bold)),
                        fontSize = 12.sp,
                        overflow = TextOverflow.Clip, // یا .Visible برای اطمینان از نمایش کامل
                    )

                    Column(
                        Modifier
                            .fillMaxWidth(0.91f)
                            .padding(bottom = screenHeight * 0.021f)
                            .align(Alignment.CenterHorizontally)
                    ) {

                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(scrollState)
                        ){
                                    if (userId.length > 0 && password.length > 0 && captchaInput.length > 5){
                                        enableLoginAcademic = true
                                    }else {
                                        enableLoginAcademic = false
                                    }

                            Spacer(modifier = Modifier.height(screenHeight * 0.008f))

                            val userIdText = if (currentLang == "fa") convertEnglishDigitsToPersian(userId) else userId
                            OutlinedTextField(
                                        value = userIdText,
                                        onValueChange = {
                                            if (it.length <= maxLengtUserId) {
                                                    userId = it
                                            }
                                        },
                                       modifier = Modifier
                                            .fillMaxWidth()
                                            .focusRequester(focusUserId),
                                        placeholder = { Text(stringResource(id = R.string.student_staff_id),
                                            fontFamily = FontFamily(Font(R.font.ir_medium)),
                                            fontSize = 12.sp) },
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = NationsBlue,
                                            unfocusedBorderColor = GrayC,
                                            focusedLabelColor = NationsBlue,
                                            unfocusedLabelColor = GrayC,
                                            focusedLeadingIconColor = NationsBlue,
                                            unfocusedLeadingIconColor = GrayC,
                                            cursorColor = GrayC,
                                            errorBorderColor = Err,
                                            errorLeadingIconColor = Err,
                                            errorTextColor = Err,
                                            errorLabelColor = Err,
                                            focusedTextColor = NationsBlue,
                                            unfocusedTextColor = GrayC
                                        ),
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.vc_school),
                                                contentDescription = null,
                                            )
                                        },
                                        shape = RoundedCornerShape(12.dp),
                                        singleLine = true,
                                        isError = errorUserId,
                                        keyboardOptions = KeyboardOptions.Default.copy(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Next),
                                        keyboardActions = KeyboardActions(
                                            onNext = { focusPassword.requestFocus() }
                                        )
                                    )

                                    AnimatedVisibility(
                                        visible = if (errorUserId) true else false,
                                    ){
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = screenHeight * 0.008f),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.error_student_id),
                                                Modifier
                                                    .fillMaxWidth(0.9f)
                                                    .padding(end = screenWidth * 0.009f),
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

                            Spacer(modifier = Modifier.height(screenHeight * 0.008f))

                            val passwordText = if (currentLang == "fa") convertEnglishDigitsToPersian(password) else password
                            OutlinedTextField(
                                        value = passwordText,
                                        onValueChange = {
                                            if (it.length <= maxLengtPassword) {
                                                password = it
                                            }
                                        },
                                       modifier = Modifier
                                            .fillMaxWidth()
                                            .focusRequester(focusPassword),
                                        singleLine = true,
                                        placeholder = { Text(stringResource(id = R.string.password),
                                            fontFamily = FontFamily(Font(R.font.ir_medium)),
                                            fontSize = 12.sp) },
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = NationsBlue,
                                            unfocusedBorderColor = GrayC,
                                            focusedLabelColor = NationsBlue,
                                            unfocusedLabelColor = GrayC,
                                            cursorColor = GrayC,
                                            errorBorderColor = Err,
                                            errorLeadingIconColor = Err,
                                            errorTextColor = Err,
                                            errorLabelColor = Err,
                                            errorTrailingIconColor = Err,
                                            focusedLeadingIconColor = NationsBlue,
                                            unfocusedLeadingIconColor = GrayC,
                                            focusedTextColor = NationsBlue,
                                            unfocusedTextColor = GrayC,
                                            focusedTrailingIconColor = NationsBlue,
                                            unfocusedTrailingIconColor = GrayC
                                        ),
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.vc_lock_open),
                                                contentDescription = null,
                                            )
                                        },
                                        trailingIcon = {
                                            Icon(
                                                painter = painterResource(id = if (isPasswordHide) R.drawable.vc_eye_off else R.drawable.vc_eye),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .pointerInput(Unit){
                                                        detectTapGestures {
                                                            isPasswordHide != isPasswordHide
                                                        }
                                                    }
                                            )
                                        },
                                        shape = RoundedCornerShape(12.dp),
                                        visualTransformation = if (isPasswordHide) PasswordVisualTransformation() else VisualTransformation.None ,
                                        isError = errorPassword,
                                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                                        keyboardActions = KeyboardActions(
                                            onNext = { focusCapcha.requestFocus() }
                                        )
                                    )
                            Spacer(modifier = Modifier.height(screenHeight * 0.008f))

                            val captchaInputText = if (currentLang == "fa") convertEnglishDigitsToPersian(captchaInput) else captchaInput
                            Row {
                                OutlinedTextField(
                                    value = captchaInputText,
                                    onValueChange = {
                                        if (it.length <= maxLengtCaptcha) {
                                            captchaInput = it
                                        }
                                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(0.60f)
                                        .padding(end = screenWidth * 0.019f)
                                        .focusRequester(focusCapcha),
                                    placeholder = { Text(stringResource(id = R.string.enter_the_captcha),
                                        fontFamily = FontFamily(Font(R.font.ir_medium)),
                                        fontSize = 12.sp) },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = NationsBlue,
                                        unfocusedBorderColor = GrayC,
                                        focusedLabelColor = NationsBlue,
                                        unfocusedLabelColor = GrayC,
                                        cursorColor = GrayC,
                                        errorBorderColor = Err,
                                        errorLeadingIconColor = Err,
                                        errorTextColor = Err,
                                        errorLabelColor = Err,
                                        focusedLeadingIconColor = NationsBlue,
                                        unfocusedLeadingIconColor = GrayC,
                                        focusedTextColor = NationsBlue,
                                        unfocusedTextColor = GrayC
                                    ),
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.vc_shield_half),
                                            contentDescription = null,
                                            )
                                                  },
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                                    keyboardActions = KeyboardActions(
                                        onDone = { focusManager.clearFocus() }
                                    )
                                )
                                CaptchaImage(captchaText = captchaText,(screenWidth.value * 0.485f),(screenHeight.value * 0.16f))
                            }

                            Spacer(modifier = Modifier.height(screenHeight * 0.008f))
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.Start)
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.forgot_password),
                                            fontFamily = FontFamily(Font(R.font.ir_medium)),
                                            fontSize = 12.sp,
                                            color = Gunmetal,
                                        )

                                        Text(
                                            stringResource(id = R.string.recover_password),
                                            Modifier
                                                .pointerInput(Unit) {
                                                    detectTapGestures {
                                                        onRecoveryPassword()
                                                    }
                                                },
                                            fontFamily = FontFamily(Font(R.font.ir_medium)),
                                            fontSize = 12.sp,
                                            color = Primary)
                                    }

                            Spacer(modifier = Modifier.height(screenHeight * 0.017f))
                            val isLogin = getisLogin(context)
                                    Button(
                                        onClick = {
                                                if (isLogin){
                                                    onLoginHome()
                                                }else {
                                                    setisLogin(context,true)
                                                    onLoginStudent()
                                                }
                                        },
                                        Modifier
                                            .fillMaxWidth()
                                            .height(screenHeight * 0.061f),
                                        shape = RoundedCornerShape(12.dp),
                                        enabled = enableLoginAcademic,
                                        colors = ButtonDefaults. buttonColors(
                                            disabledContentColor = GrayE,
                                            disabledContainerColor = Color(0XFFF3F3F4),
                                            containerColor = NationsBlue,
                                            contentColor = White
                                        )
                                    ) {

                                        Icon(
                                            imageVector = if (currentLang == "fa") Icons.Default.ArrowForward else Icons.Default.ArrowBack,
                                            contentDescription = null )
                                        Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                        Text(stringResource(id = R.string.button_login),
                                            fontFamily = FontFamily(Font(R.font.ir_medium)),
                                            fontSize = 14.sp)
                                    }
                            Spacer(modifier = Modifier.height(screenHeight * 0.017f))
                                    Button(
                                        onClick = {
                                            onAuthentication()
                                        },
                                        Modifier
                                            .fillMaxWidth()
                                            .height(screenHeight * 0.061f),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults. buttonColors(
                                            disabledContentColor = GrayE,
                                            disabledContainerColor = Color(0XFFF3F3F4),
                                            containerColor = NationsBlue,
                                            contentColor = White
                                        )
                                    ) {

                                        Icon(painter = painterResource(id = R.drawable.vc_key_backup), contentDescription = null )
                                        Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                        Text(stringResource(id = R.string.recover_account),
                                            fontFamily = FontFamily(Font(R.font.ir_medium)),
                                            fontSize = 14.sp)
                                    }
                        }
                    }
            }

            Row(
                Modifier
                    .fillMaxHeight(0.1f)
                    .padding(top = screenHeight * 0.06f, start = screenWidth * 0.068f)
                    .align(Alignment.TopStart)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = Blue200)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            val newLang = if (currentLang == "fa") "en" else "fa"
                            setlang(context, newLang)
                            activity?.recreate() // بازسازی اکتیویتی برای اعمال زبان جدید
                        }
                    }
                    .padding(vertical = screenHeight * 0.008f, horizontal = screenWidth * 0.02f)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_iran), contentDescription = "logo",
                    Modifier
                        .fillMaxWidth(0.06f),
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.019f))
                Text(
                    text = stringResource(id = R.string.lnguage),
                    modifier = Modifier.padding(horizontal = screenWidth * 0.019f)
                        .fillMaxHeight(),
                    fontFamily = FontFamily(Font(R.font.ir_medium)),
                    fontSize = 12.sp,
                    color = Gunmetal
                )
                Icon(
                    imageVector = if (currentLang == "fa") Icons.Default.KeyboardArrowLeft else Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Gunmetal,
                    modifier = Modifier
                        .padding(horizontal = screenWidth * 0.004f)
                )
            }

            Text(
                text = stringResource(id = R.string.app_version),
                Modifier
//                    .padding(bottom = screenHeight * 0.058f)
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
fun LoginPreview() {
    SinarTheme {
        LoginScreen()
    }
}