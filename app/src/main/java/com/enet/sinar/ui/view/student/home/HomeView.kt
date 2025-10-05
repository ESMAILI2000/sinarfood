package com.enet.sinar.ui.view.student.home

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.enet.sinar.R
import com.enet.sinar.ui.theme.Background
import com.enet.sinar.ui.theme.EerieBlack
import com.enet.sinar.ui.theme.Err
import com.enet.sinar.ui.theme.GargoyleGas
import com.enet.sinar.ui.theme.Gray04
import com.enet.sinar.ui.theme.GrayC
import com.enet.sinar.ui.theme.GrayE
import com.enet.sinar.ui.theme.GrayF
import com.enet.sinar.ui.theme.Gunmetal
import com.enet.sinar.ui.theme.NationsBlue
import com.enet.sinar.ui.theme.PoliceBlue
import com.enet.sinar.ui.theme.SinarTheme
import com.enet.sinar.ui.theme.Succes
import com.enet.sinar.ui.theme.Water
import com.enet.sinar.ui.theme.White
import com.enet.sinar.ui.utility.MySharedPreferences
import com.enet.sinar.ui.view.BottomBarItem
import com.enet.sinar.ui.view.MenuItem
import com.enet.sinar.ui.view.TextFeildItem
import com.enet.sinar.ui.view.convertEnglishDigitsToPersian
import com.enet.sinar.ui.view.custom_view.CustomBottomNavigationBar
import com.enet.sinar.ui.view.custom_view.DashedContainer
import com.enet.sinar.ui.view.custom_view.EllipsizedMiddleText
import com.enet.sinar.ui.view.custom_view.HexagonShape
import com.enet.sinar.ui.view.formatWithDots


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun StudentHomeScreen(
    onNavigateToInvoice: () -> Unit = {},
    onNavigateToPayment: () -> Unit = {},
    onNavigateToTransaction: () -> Unit = {},
    onNavigateToExchange: () -> Unit = {},
    onNavigateToFoodReservation: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    modifier: Modifier = Modifier) {


    var isVisibleHeader by remember { mutableStateOf(false) }
    var isDialogSetting by remember { mutableStateOf(false) }
    var isDialogMenu by remember { mutableStateOf(false) }
    var isDialogEditProfile by remember { mutableStateOf(false) }
    var isDialogExit by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState() // اسکرول برای دبالوگ تنظیمات
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current // برای کپی کردن
    val activity = context as? Activity // برای خروج از برنامه

    var lastBackPressedTime by remember { mutableStateOf(0L) }
    // کنترل دکمه بک
    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBackPressedTime < 1000) {
            activity?.finishAffinity() // خروج کامل از برنامه
        } else {
            isDialogExit = true
            lastBackPressedTime = currentTime
        }
    }

    val currentLang = MySharedPreferences.getLang(context)

    val itemsSetting = listOf(
        MenuItem(stringResource(id = R.string.profile_settings), painterResource(id = R.drawable.vc_user_circle)),
        MenuItem(stringResource(id = R.string.fingerprint_settings), painterResource(id = R.drawable.vc_fingerprint)),
        MenuItem(stringResource(id = R.string.verification_levels), painterResource(id = R.drawable.vc_shield_lock)),
        MenuItem(stringResource(id = R.string.about_us), painterResource(id = R.drawable.vc_users)),
        MenuItem(stringResource(id = R.string.white_paper), painterResource(id = R.drawable.vc_notes))
    )

    val itemsMenu = listOf(
        MenuItem(stringResource(id = R.string.add_network), painterResource(id = R.drawable.vc_topology_full)),
        MenuItem(stringResource(id = R.string.token_balances), painterResource(id = R.drawable.vc_wallet)),
        MenuItem(stringResource(id = R.string.view_private_keys), painterResource(id = R.drawable.vc_eye)),
        MenuItem(stringResource(id = R.string.add_token), painterResource(id = R.drawable.vc_plus)),
        )

    val itemsEditProfile = listOf(
        TextFeildItem("", painterResource(id = R.drawable.vc_user), value = if (currentLang == "fa") mutableStateOf("ریحانه کشاورز حدادها") else mutableStateOf("Reyhaneh")),
        TextFeildItem("", painterResource(id = R.drawable.vc_school),mutableStateOf("9922319003"), isNumeric = true),
        TextFeildItem("", painterResource(id = R.drawable.vc_university),if (currentLang == "fa") mutableStateOf("دانشگاه شیراز") else mutableStateOf("Shiraz University")),
        TextFeildItem("", painterResource(id = R.drawable.vc_phone_call),mutableStateOf("09227049474"), isNumeric = true),
        TextFeildItem(stringResource(id = R.string.new_password), painterResource(id = R.drawable.vc_lock),mutableStateOf(""), isPassword = true),
        TextFeildItem(stringResource(id = R.string.confirm_new_password), painterResource(id = R.drawable.vc_lock_open),mutableStateOf(""), isPassword = true),
    )

    val layoutDirection = if (currentLang == "fa") LayoutDirection.Ltr else LayoutDirection.Rtl

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        BoxWithConstraints (
            modifier = Modifier
        ) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            Scaffold(
                topBar = {
                    CompositionLocalProvider(LocalLayoutDirection provides if (currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr){
                        TopAppBar(
                            title = { Text(
                                text =stringResource(id = R.string.app_name_title),
                                style = MaterialTheme.typography.titleLarge,
                                color = Gunmetal
                            ) },
                            actions = {
                                Icon(
                                    painter = painterResource(id = R.drawable.vc_settings),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .pointerInput(Unit) {
                                            detectTapGestures {
                                                isDialogSetting = true
                                            }
                                        })
                            },
                            navigationIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.vc_menu),
                                    contentDescription = null,
                                    tint = Gunmetal,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .pointerInput(Unit) {
                                            detectTapGestures {
                                                isDialogMenu = true
                                            }
                                        })
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Background,
                                titleContentColor = Color.Black,
                                actionIconContentColor = Color.Black
                            ),
                            windowInsets = TopAppBarDefaults.windowInsets
                        )
                    }
                },
                bottomBar = {
                    var selectedItem by remember { mutableStateOf(3) }
                    val items = listOf(
                        BottomBarItem(stringResource(id = R.string.my_miner), painterResource(id = R.drawable.vc_hammer_crash), painterResource(id = R.drawable.vc_filled_hammer_crash)),
                        BottomBarItem(stringResource(id = R.string.rothbard), painterResource(id = R.drawable.vc_student), painterResource(id = R.drawable.vc_filled_student)),
                        BottomBarItem(stringResource(id = R.string.library), painterResource(id = R.drawable.vc_book_alt), painterResource(id = R.drawable.vc_filled_book_alt)),
                        BottomBarItem(stringResource(id = R.string.home), painterResource(id = R.drawable.vc_home), painterResource(id = R.drawable.vc_filled_home))
                    )

                    Box(
                        Modifier
                            .background(White)
                            .fillMaxWidth()
                            .offset(y=-16.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Box(modifier = Modifier
                            .background(White)
                            .size(screenWidth * 0.92f, 84.dp))
                        {
                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.9f)
                                .background(Background, RoundedCornerShape(16.dp))
                                .align(Alignment.BottomCenter))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.92f)
                                    .align(Alignment.TopCenter)
                                    .fillMaxHeight(0.9f)
                                    .offset(y=screenHeight * 0.009f),
                            ){
                                Image(
                                    painter = painterResource(id = R.drawable.vc_half_circle),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .offset(x = (screenWidth * 0.194f * selectedItem)) // برای اینکه با انتخاب آیتم نیم دایره سفید هم جا به جا شود
                                        .align(Alignment.TopStart)
                                        .width(screenWidth * 0.264f)
                                )
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.TopCenter)
                                    .fillMaxHeight(0.9f),
                                horizontalArrangement = Arrangement.Center) {
                                items.forEachIndexed { index, item ->

                                    Box(modifier = Modifier
                                        .size(screenWidth * 0.194f, 75.dp)
                                    ){
                                        Column(
                                            modifier = Modifier
                                                .align(if (selectedItem == index) Alignment.TopCenter else Alignment.BottomCenter)
                                                .width(screenWidth * 0.145f)
                                                .height(65.dp)
                                                .pointerInput(Unit) {
                                                    detectTapGestures {
                                                        selectedItem = index
                                                    }
                                                },
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Top
                                        ) {
                                            Box(modifier = Modifier
                                                .size(48.dp)
                                                .background(
                                                    if (selectedItem == index) GargoyleGas else Background,
                                                    CircleShape
                                                ),
                                                contentAlignment = Alignment.Center
                                            ){
                                                Icon(
                                                    modifier = Modifier
                                                        .size(24.dp),
                                                    painter =  if (selectedItem != index) item.icon else item.selectedIcon,
                                                    contentDescription = item.text,
                                                    tint = if (selectedItem == index) Color.Black else Water
                                                )
                                            }

                                             if (selectedItem != index){
                                                Text(
                                                    text = item.text,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = Water
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .background(White),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){

                        CompositionLocalProvider(LocalLayoutDirection provides if(currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr ){
                            // دیالوگ خروج از برنامه
                            if (isDialogExit){
                                Dialog(
                                    onDismissRequest = {
                                        isDialogExit = false
                                    },
                                    properties = DialogProperties(usePlatformDefaultWidth = false)
                                ) {
                                    Column {
                                        // برای اینکه دیالوگ پایین صفحه قرار بگیرد
                                        Spacer(modifier = Modifier.height(screenHeight * 0.27f))

                                        Column( // محتوای اصلی دیالوگ
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
                                                    .align(Alignment.End)
                                                    .padding(
                                                        end = screenWidth * 0.04f,
                                                        top = screenHeight * 0.017f
                                                    )
                                                    .size(24.dp)
                                                    .pointerInput(Unit) {
                                                        detectTapGestures {
                                                            isDialogExit = false
                                                        }
                                                    }
                                            )
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.ic_logout),
                                                    contentDescription = "",
                                                    Modifier.size(68.dp)
                                                )

                                                Spacer(modifier = Modifier.height(screenHeight * 0.017f))

                                                Text(
                                                    text = stringResource(id = R.string.exit_application),
                                                    style = MaterialTheme.typography.displayMedium,
                                                    color = Gunmetal
                                                )

                                                Spacer(modifier = Modifier.height(screenHeight * 0.008f))

                                                Text(
                                                    text = stringResource(id = R.string.are_you_exit),
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    color = GrayF
                                                )

                                                Spacer(modifier = Modifier.height(screenHeight * 0.026f))

                                                CompositionLocalProvider(LocalLayoutDirection provides if(currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr ){
                                                    Row(
                                                        Modifier.width(screenWidth * 0.8f),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        OutlinedButton(
                                                            onClick = {
                                                                isDialogExit = false
                                                            },
                                                            modifier = Modifier
                                                                .width(screenWidth * 0.383f)
                                                                .height(53.dp),
                                                            shape = RoundedCornerShape(12.dp),
                                                            border = BorderStroke(1.dp, Err)
                                                        ) {
                                                            Text(
                                                                text = stringResource(id = R.string.cancel),
                                                                style = MaterialTheme.typography.bodyLarge,
                                                                color = Err
                                                            )
                                                        }

                                                        FilledTonalButton(
                                                            onClick = {
                                                                activity?.finishAffinity() // بستن برنامه
                                                            },
                                                            modifier = Modifier
                                                                .width(screenWidth * 0.383f)
                                                                .height(53.dp),
                                                            shape = RoundedCornerShape(12.dp),
                                                            colors =  ButtonDefaults. outlinedButtonColors(
                                                                containerColor = Err,
                                                                contentColor = White
                                                            )
                                                        ) {
                                                            Text(
                                                                text = stringResource(id = R.string.exit_application),
                                                                style = MaterialTheme.typography.bodyLarge,
                                                                textAlign = TextAlign.Center
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

                            if (isDialogMenu){
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Dialog(
                                        onDismissRequest = {
                                            isDialogMenu = false
                                        },
                                        properties = DialogProperties(
                                            usePlatformDefaultWidth = false)
                                    ) {
                                        Column(modifier = Modifier
                                            .fillMaxWidth(0.88f),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f)
                                        ){
                                            Column( // دیالوگ اصلی
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(White, RoundedCornerShape(32.dp)),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {

                                                Column(
                                                    modifier = Modifier.padding(top = screenHeight * 0.017f),
                                                    verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    HorizontalDivider(
                                                        modifier = Modifier
                                                            .width(screenWidth * 0.06f),
                                                        thickness = 4.dp,
                                                        color = Gray04
                                                    )
                                                    CompositionLocalProvider(LocalLayoutDirection provides if(currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr ){
                                                        itemsMenu.forEachIndexed { index, menuItem ->
                                                            Row(
                                                                modifier = Modifier
                                                                    .size(screenWidth * 0.8f, 60.dp)
                                                                    .background(
                                                                        Color(0xFFF0F9FF),
                                                                        RoundedCornerShape(16.dp)
                                                                    ),
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Row(
                                                                    modifier = Modifier
                                                                        .fillMaxWidth(0.8f)
                                                                        .padding(start = screenWidth * 0.06f),
                                                                    horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.03f),
                                                                    verticalAlignment = Alignment.CenterVertically
                                                                ) {
                                                                    Icon(
                                                                        painter = itemsMenu[index].icon,
                                                                        contentDescription = "",
                                                                        tint = NationsBlue,
                                                                        modifier = Modifier
                                                                            .size(24.dp)
                                                                    )
                                                                    Text(
                                                                        text = itemsMenu[index].text,
                                                                        style = MaterialTheme.typography.bodyLarge.copy(
                                                                            fontWeight = FontWeight.Bold
                                                                        ),
                                                                        color = EerieBlack
                                                                    )
                                                                }
                                                                Box(
                                                                    modifier = Modifier.fillMaxWidth(),
                                                                    contentAlignment = Alignment.CenterStart
                                                                ) {
                                                                    Icon(
                                                                        imageVector = if (currentLang == "fa") Icons.Default.KeyboardArrowLeft else Icons.Default.KeyboardArrowRight,
                                                                        contentDescription = "",
                                                                        tint = EerieBlack,
                                                                        modifier = Modifier
                                                                            .padding(start = screenWidth * 0.06f)
                                                                            .size(20.dp)
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }

                                                    Spacer(modifier = Modifier.height(screenHeight * 0.008f))
                                                }
                                            }

                                            FloatingActionButton(
                                                onClick = {
                                                    isDialogMenu = false
                                                },
                                                modifier = Modifier
                                                    .size(56.dp),
                                                shape = CircleShape,
                                                contentColor = Color.Black,
                                                containerColor = White,
                                                ) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.vc_close),
                                                    contentDescription = "",
                                                    modifier = Modifier.size(32.dp)
                                                )
                                            }
                                            //
                                        }
                                    }
                                }
                            }

                            if (isDialogSetting){
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val activity = LocalContext.current as Activity
                                    SideEffect {
                                        // دوباره پنهان کردن نوار ناوبری
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                            activity.window.setDecorFitsSystemWindows(false)
                                            val controller = activity.window.insetsController
                                            controller?.hide(android.view.WindowInsets.Type.navigationBars())
                                            controller?.systemBarsBehavior =
                                                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                                        } else {
                                            @Suppress("DEPRECATION")
                                            activity.window.decorView.systemUiVisibility = (
                                                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                                                    )
                                        }
                                    }
                                    Dialog(
                                        onDismissRequest = {
                                            isDialogSetting = false
                                        },
                                        properties = DialogProperties(
                                            usePlatformDefaultWidth = false)
                                    ){
                                        Column(modifier = Modifier
                                            .fillMaxWidth(0.88f),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f)
                                        ){
                                            Column( // دیالوگ اصلی
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight(0.8f)
                                                    .background(White, RoundedCornerShape(32.dp))
                                                    .clip(RoundedCornerShape(32.dp)),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Column ( // برای اینکه ارتفاع دیالوگ زیاد نشه که دکمه زیرش پنهان بشه
                                                    modifier = Modifier
                                                        .verticalScroll(scrollState),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    HorizontalDivider(
                                                        Modifier
                                                            .width(screenWidth * 0.06f)
                                                            .padding(top = screenHeight * 0.017f),
                                                        thickness = 4.dp,
                                                        color = Gray04
                                                    )

                                                    Box(modifier = Modifier
                                                        .offset(y = screenHeight * 0.017f)
                                                        .size(57.dp, 64.dp)
                                                    ){
                                                        Image(painter = painterResource(id = R.drawable.ic_up),
                                                            contentDescription ="",
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .align(Alignment.TopCenter)
                                                                .clip(HexagonShape()),
                                                            contentScale = ContentScale.Crop
                                                        )
                                                        Icon(painter = painterResource(id = R.drawable.vc_camera_plus) ,
                                                            contentDescription ="" ,
                                                            modifier = Modifier
                                                                .size(24.dp)
                                                                .align(Alignment.BottomCenter)
                                                                .border(
                                                                    BorderStroke(1.dp, NationsBlue),
                                                                    CircleShape
                                                                )
                                                                .background(White, CircleShape)
                                                                .padding(4.dp),
                                                            tint = NationsBlue)

                                                    }

                                                    Text(text = if (currentLang =="fa") "ریحانه کشاورز حدادها" else "Reyhaneh Keshavarz",
                                                        style = MaterialTheme.typography.displayLarge,
                                                        modifier = Modifier
                                                            .padding(top = screenHeight * 0.017f),
                                                        color = Gunmetal)
                                                    Row(
                                                        Modifier
                                                            .padding(top = screenHeight * 0.008f),
                                                        horizontalArrangement = Arrangement.Center,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Icon(painter = painterResource(id = R.drawable.vc_school),
                                                            contentDescription = "",
                                                            tint = GrayF,
                                                            modifier =  Modifier.size(20.dp))

                                                        Text(text = if (currentLang == "fa"){
                                                            convertEnglishDigitsToPersian("43167487998")
                                                        }else{
                                                            "43167487998"
                                                        },
                                                            maxLines = 1,
                                                            color = GrayF,
                                                            style = MaterialTheme.typography.bodySmall,
                                                            textAlign = TextAlign.Start,
                                                            modifier = Modifier
                                                                .padding(start = screenWidth * 0.02f))
                                                    }
                                                    Column(
                                                        modifier = Modifier.padding(top = screenHeight * 0.017f),
                                                        verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f)
                                                    ) {

                                                        itemsSetting.forEachIndexed { index, menuItem ->
                                                            CompositionLocalProvider(LocalLayoutDirection provides if(currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr ) {
                                                                Row(
                                                                    modifier = Modifier
                                                                        .size(
                                                                            screenWidth * 0.8f,
                                                                            60.dp
                                                                        )
                                                                        .background(
                                                                            Color(0xFFF0F9FF),
                                                                            RoundedCornerShape(16.dp)
                                                                        )
                                                                        .pointerInput(Unit) {
                                                                            detectTapGestures {
                                                                                if (index == 0) {
                                                                                    isDialogEditProfile = true
                                                                                } else if (index == 7){
                                                                                    isDialogExit = true
                                                                                    isDialogSetting = false
                                                                                }

                                                                            }
                                                                        },
                                                                    verticalAlignment = Alignment.CenterVertically
                                                                ) {
                                                                    Row(
                                                                        modifier = Modifier
                                                                            .fillMaxWidth(0.8f)
                                                                            .padding(start = screenWidth * 0.06f),
                                                                        horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.03f),
                                                                        verticalAlignment = Alignment.CenterVertically
                                                                    ) {
                                                                        Icon(
                                                                            painter = itemsSetting[index].icon ,
                                                                            contentDescription = "",
                                                                            tint = NationsBlue,
                                                                            modifier = Modifier
                                                                                .size(24.dp)
                                                                        )
                                                                        Text(text = itemsSetting[index].text,
                                                                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                                                            color = EerieBlack)
                                                                    }

                                                                    Box(
                                                                        modifier = Modifier.fillMaxWidth(),
                                                                        contentAlignment = Alignment.CenterEnd){
                                                                        Icon(
                                                                            imageVector = if (currentLang == "fa") Icons.Default.KeyboardArrowLeft else Icons.Default.KeyboardArrowRight,
                                                                            contentDescription = "" ,
                                                                            tint = EerieBlack,
                                                                            modifier = Modifier
                                                                                .padding(end = screenWidth * 0.06f)
                                                                                .size(20.dp)
                                                                        )
                                                                    }
                                                                }
                                                            }

                                                        }

                                                        CompositionLocalProvider(LocalLayoutDirection provides if(currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr ){
                                                            Row(
                                                                modifier = Modifier
                                                                    .size(screenWidth * 0.8f, 60.dp)
                                                                    .background(
                                                                        Color(0xFFF0F9FF),
                                                                        RoundedCornerShape(16.dp)
                                                                    ),
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Row(
                                                                    Modifier.padding(start = screenWidth * 0.06f),
                                                                    horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.03f)
                                                                ) {
                                                                    Icon(
                                                                        painter = painterResource(id = R.drawable.vc_cube_sphere) ,
                                                                        contentDescription = "",
                                                                        tint = NationsBlue,
                                                                        modifier = Modifier
                                                                            .size(24.dp)
                                                                    )
                                                                    Text(text = stringResource(id = R.string.airdrop),
                                                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                                                        color = EerieBlack)
                                                                }

                                                            }

                                                            Row(
                                                                modifier = Modifier
                                                                    .size(screenWidth * 0.8f, 60.dp)
                                                                    .background(
                                                                        Color(0xFFF0F9FF),
                                                                        RoundedCornerShape(16.dp)
                                                                    ),
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Row(
                                                                    modifier = Modifier
                                                                        .fillMaxWidth(0.5f)
                                                                        .padding(start = screenWidth * 0.06f),
                                                                    horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.03f),
                                                                    verticalAlignment = Alignment.CenterVertically
                                                                ) {
                                                                    Icon(
                                                                        painter = painterResource(id = R.drawable.vc_moon) ,
                                                                        contentDescription = "",
                                                                        tint = NationsBlue,
                                                                        modifier = Modifier
                                                                            .size(24.dp)
                                                                    )
                                                                    Text(text = stringResource(id = R.string.app_theme),
                                                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                                                        color = EerieBlack)
                                                                }

                                                                Box(
                                                                    modifier = Modifier.fillMaxWidth(),
                                                                    contentAlignment = Alignment.CenterEnd
                                                                ){
                                                                    Switch(
                                                                        checked = isDarkMode,
                                                                        onCheckedChange = {
                                                                            isDarkMode = !isDarkMode
                                                                        },
                                                                        modifier = Modifier
                                                                            .padding(end = screenWidth * 0.05f)
                                                                            .width(48.dp)
                                                                            .height(24.dp),
                                                                        enabled = true,
                                                                        colors = SwitchDefaults. colors(
                                                                            checkedTrackColor = Color(0xFF8BB9F2).copy(alpha = 0.5f),
                                                                            uncheckedTrackColor = Color(0xFF8BB9F2).copy(alpha = 0.5f),
                                                                            checkedThumbColor = White,
                                                                            uncheckedThumbColor = White,
                                                                            checkedBorderColor = Color.Transparent,
                                                                            uncheckedBorderColor = Color.Transparent,
                                                                        ),
                                                                        thumbContent = {
                                                                            Icon(
                                                                                painter = painterResource(id = if (!isDarkMode) R.drawable.vc_moon else  R.drawable.vc_sun_filled) ,
                                                                                contentDescription = "",
                                                                                tint = if (!isDarkMode) NationsBlue else GargoyleGas,
                                                                                modifier = Modifier
                                                                                    .size(12.dp)
                                                                            )
                                                                        }
                                                                    )
                                                                }

                                                            }

                                                            Row(
                                                                modifier = Modifier
                                                                    .size(screenWidth * 0.8f, 60.dp)
                                                                    .background(
                                                                        Color(0xFFF0F9FF),
                                                                        RoundedCornerShape(16.dp)
                                                                    ),
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Row(
                                                                    Modifier
                                                                        .padding(start = screenWidth * 0.06f)
                                                                        .pointerInput(Unit) {
                                                                            detectTapGestures {
                                                                                isDialogSetting =
                                                                                    false
                                                                                isDialogExit = true
                                                                            }
                                                                        },
                                                                    horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.03f)
                                                                ) {
                                                                    Icon(
                                                                        painter = painterResource(id = R.drawable.vc_power) ,
                                                                        contentDescription = "",
                                                                        tint = NationsBlue,
                                                                        modifier = Modifier
                                                                            .size(24.dp)
                                                                    )
                                                                    Text(text = stringResource(id = R.string.logout),
                                                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                                                        color = EerieBlack)
                                                                }

                                                            }
                                                        }


                                                        Spacer(modifier = Modifier.height(screenHeight * 0.008f))

                                                    }
                                                }
                                            }

                                            FloatingActionButton(
                                                onClick = {
                                                    isDialogSetting = false
                                                },
                                                modifier = Modifier
                                                    .size(56.dp),
                                                shape = CircleShape,
                                                contentColor = Color.Black,
                                                containerColor = White
                                                ) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.vc_close),
                                                    contentDescription = "",
                                                    modifier = Modifier.size(32.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            if (isDialogEditProfile){
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Dialog(
                                        onDismissRequest = {
                                            isDialogEditProfile = false
                                            isDialogSetting = false
                                        },
                                        properties = DialogProperties(
                                            usePlatformDefaultWidth = false)
                                    ) {
                                        Column(modifier = Modifier
                                            .fillMaxWidth(0.88f),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f)
                                        ){
                                            Column( // دیالوگ اصلی
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(White, RoundedCornerShape(32.dp)),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                HorizontalDivider(
                                                    Modifier
                                                        .width(screenWidth * 0.06f)
                                                        .padding(top = screenHeight * 0.017f),
                                                    thickness = 4.dp,
                                                    color = Gray04
                                                )
                                                Icon(
                                                    imageVector = if (currentLang == "fa") Icons.Default.ArrowBack else Icons.Default.ArrowForward,
                                                    contentDescription = "" ,
                                                    tint = EerieBlack,
                                                    modifier = Modifier
                                                        .align( if (currentLang == "fa") Alignment.Start else Alignment.End)
                                                        .padding(horizontal = screenWidth * 0.04f)
                                                        .size(24.dp)
                                                        .pointerInput(Unit) {
                                                            detectTapGestures {
                                                                isDialogEditProfile = false
                                                                isDialogSetting = false
                                                            }
                                                        }
                                                )
                                                CompositionLocalProvider(LocalLayoutDirection provides if(currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr ){
                                                    Column(
                                                        modifier = Modifier.padding(top = screenHeight * 0.017f),
                                                        verticalArrangement = Arrangement.spacedBy(screenHeight * 0.008f)
                                                    ) {
                                                        itemsEditProfile.forEachIndexed { index, menuItem ->
                                                            OutlinedTextField(
                                                                value = if (currentLang == "fa"){
                                                                    convertEnglishDigitsToPersian(menuItem.value.value)
                                                                }else{
                                                                    menuItem.value.value
                                                                },
                                                                onValueChange = {
                                                                    if (currentLang == "fa"){
                                                                        menuItem.value.value = convertEnglishDigitsToPersian(it)
                                                                    }else{
                                                                        menuItem.value.value = it
                                                                    }

                                                                },
                                                                placeholder = {
                                                                    Text(
                                                                        text = menuItem.holder,
                                                                        style = MaterialTheme.typography.bodySmall)
                                                                },
                                                                leadingIcon = {
                                                                    Icon(
                                                                        painter = menuItem.icon,
                                                                        contentDescription = null
                                                                    )
                                                                },
                                                                shape = RoundedCornerShape(12.dp),
                                                                colors = OutlinedTextFieldDefaults.colors(
                                                                    unfocusedBorderColor = if (menuItem.isPassword) GrayC else GrayE,
                                                                    focusedBorderColor = NationsBlue,
                                                                    errorBorderColor = Err,
                                                                    focusedLeadingIconColor = NationsBlue,
                                                                    unfocusedLeadingIconColor = if (menuItem.isPassword) GrayC else GrayE,
                                                                    errorLeadingIconColor = Err,
                                                                    focusedTextColor = NationsBlue,
                                                                    unfocusedTextColor = if (menuItem.isPassword) GrayC else GrayE,
                                                                    errorTextColor = Err,
                                                                    focusedPlaceholderColor = NationsBlue,
                                                                    unfocusedPlaceholderColor = if (menuItem.isPassword) GrayC else GrayE,
                                                                    errorPlaceholderColor = Err
                                                                ),
                                                                keyboardOptions = KeyboardOptions(
                                                                    keyboardType = when {
                                                                        menuItem.isPassword -> KeyboardType.Password
                                                                        menuItem.isNumeric -> KeyboardType.Number
                                                                        else -> KeyboardType.Text
                                                                    }
                                                                ),
                                                                visualTransformation = if (menuItem.isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                                                                maxLines = 1,
                                                                modifier = Modifier
                                                                    .size(screenWidth * 0.8f,56.dp)
                                                            )
                                                        }


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
                                                            Icon(painter = painterResource(id = R.drawable.vc_pencil) , contentDescription = null )
                                                            Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                                            Text(stringResource(id = R.string.save_changes),
                                                                fontFamily = FontFamily(Font(R.font.ir_medium)),
                                                                fontSize = 14.sp)
                                                        }

                                                        Spacer(modifier = Modifier.height(screenHeight * 0.017f))
                                                    }
                                                }


                                            }

                                            FloatingActionButton(
                                                onClick = {
                                                    isDialogEditProfile = false
                                                    isDialogSetting = false
                                                },
                                                modifier = Modifier
                                                    .size(56.dp),
                                                shape = CircleShape,
                                                contentColor = Color.Black,
                                                containerColor = White
                                                ) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.vc_close),
                                                    contentDescription = "",
                                                    modifier = Modifier.size(32.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }


                        Column(
                            Modifier
                                .fillMaxWidth()
                                .background(Background),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DashedContainer(
                                modifier = Modifier
                                    .padding(bottom = 8.dp, top = screenHeight * 0.005f)
                                    .fillMaxWidth(0.883f)
                                    .background(
                                        Color(0xFFD2EEFF),
                                        RoundedCornerShape(24.dp)
                                    ),
                                cornerRadius = 24.dp,
                                borderColor = NationsBlue,
                                dashLength = 4.dp,
                                gapLength = 5.dp
                            ) {
                                Column(modifier = Modifier
                                    .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .width(screenWidth * 0.88f)
                                            .padding(
                                                top = screenHeight * 0.017f,
                                                start = screenHeight * 0.017f,
                                                end = screenHeight * 0.017f
                                            ),
                                        verticalAlignment = Alignment.Top
                                    ){
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth(0.333f)
                                        ){
                                                    Box(modifier = Modifier
                                                        .size(32.dp)
                                                        .background(NationsBlue, CircleShape),
                                                        contentAlignment = Alignment.Center){
                                                        Icon(painter = painterResource(id = R.drawable.vc_infinity) ,
                                                            contentDescription ="" ,
                                                            modifier = Modifier.size(24.dp),
                                                            tint = White)
                                                    }
                                                }
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth(0.5f),
                                            contentAlignment = Alignment.TopCenter
                                        ){
                                            Image(painter = painterResource(id = R.drawable.ic_avatar),
                                                contentDescription ="",
                                                modifier = Modifier
                                                    .size(screenHeight * 0.07f)
                                                    .clip(HexagonShape()),
                                                contentScale = ContentScale.Crop
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth(1f),
                                            contentAlignment = Alignment.TopEnd
                                        ){
                                            Box(
                                                modifier = Modifier
                                                    .size(32.dp)
                                                    .pointerInput(Unit) {
                                                        detectTapGestures {
                                                            onNavigateToPayment()
                                                        }
                                                    }
                                                    .background(NationsBlue, CircleShape),
                                                contentAlignment = Alignment.Center
                                            ){
                                                Icon(painter = painterResource(id = R.drawable.vc_wallet) ,
                                                    contentDescription ="" ,
                                                    modifier = Modifier.size(24.dp),
                                                    tint = White)
                                            }
                                        }
                                    }
                                    Text(
                                        text = if (currentLang =="fa") "ریحانه کشاورز حدادها" else "Reyhaneh Keshavarz",
                                        style = MaterialTheme.typography.displayLarge,
                                        modifier = Modifier
                                            .padding(top = if (screenHeight > 800.dp) screenHeight * 0.017f else screenHeight * 0.002f),
                                        color = Gunmetal
                                    )
                                    
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = if (screenHeight > 800.dp) screenHeight * 0.008f else screenHeight * 0.002f),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.vc_copy),
                                            contentDescription = "",
                                            tint = Gunmetal,
                                            modifier = Modifier
                                                .size(20.dp)
                                                .pointerInput(Unit) {
                                                    detectTapGestures {
                                                        clipboardManager.setText(
                                                            AnnotatedString("0x5A141B7eba38A151EDfcd816D912E6ae5C0307b1")
                                                        )
                                                    }
                                                }
                                        )

                                        EllipsizedMiddleText(
                                            text = "0x5A141B7eba38A151EDfcd816D912E6ae5C0307b1",
                                            startLength = 10,
                                            endLength = 5,
                                            maxLines = 1,
                                            color = Gunmetal,
                                            style = MaterialTheme.typography.bodySmall,
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier
                                                .fillMaxWidth(0.4f)
                                        )
                                    }

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                top = if (screenHeight > 800.dp) screenHeight * 0.008f else screenHeight * 0.002f,
                                                start = screenWidth * 0.02f,
                                                end = screenWidth * 0.02f
                                            ),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(screenHeight * 0.008f)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            val userIdText = if (currentLang == "fa") convertEnglishDigitsToPersian("4312440001") else "4312440001"
                                            Text(text = userIdText,
                                                maxLines = 1,
                                                color = PoliceBlue,
                                                style = MaterialTheme.typography.bodyLarge,
                                                overflow = TextOverflow.Visible,
                                                modifier = Modifier.fillMaxWidth(0.5f))
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                horizontalArrangement = Arrangement.End,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(text = stringResource(id = R.string.student_staff_id),
                                                    maxLines = 1,
                                                    color = Gunmetal,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    overflow = TextOverflow.Visible,
                                                    modifier = Modifier
                                                )
                                                Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                                Icon(painter = painterResource(id = R.drawable.vc_school),
                                                    contentDescription = "",
                                                    tint = Gunmetal,
                                                    modifier =  Modifier.size(24.dp))
                                            }
                                        }

                                        Row(
                                            Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                                    Row(
                                                        Modifier
                                                            .fillMaxWidth(0.4f),
                                                        horizontalArrangement = Arrangement.Start,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Icon(imageVector = Icons.Default.KeyboardArrowDown,
                                                            contentDescription = "",
                                                            tint = Gunmetal,
                                                            modifier =  Modifier.size(20.dp))

                                                        Text(text = if (currentLang =="fa") "شبکه اکباتان" else "Ekbatan Network ",
                                                            maxLines = 1,
                                                            color = PoliceBlue,
                                                            style = MaterialTheme.typography.bodyLarge,
                                                            overflow = TextOverflow.Visible,
                                                            modifier = Modifier
                                                        )
                                                        Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                                        Image(painter = painterResource(id = R.drawable.vc_bnb),
                                                            contentDescription = "",
                                                            modifier =  Modifier.size(24.dp))
                                                    }
                                                    Row(
                                                        Modifier
                                                            .fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.End,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Text(text = stringResource(id = R.string.current_blockchain_network),
                                                            maxLines = 1,
                                                            color = Gunmetal,
                                                            style = MaterialTheme.typography.labelSmall,
                                                            overflow = TextOverflow.Visible,
                                                            modifier = Modifier
                                                        )
                                                        Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                                        Icon(painter = painterResource(id = R.drawable.vc_topology_ring),
                                                            contentDescription = "",
                                                            tint = Gunmetal,
                                                            modifier =  Modifier.size(24.dp))
                                                    }
                                                }

                                        AnimatedVisibility(visible = isVisibleHeader) {
                                            Column(
                                               modifier = Modifier
                                                    .fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
                                                        Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Row(
                                                                modifier = Modifier
                                                                    .fillMaxWidth(0.3f),
                                                                horizontalArrangement = Arrangement.Start,
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Icon(
                                                                    imageVector = Icons.Default.KeyboardArrowDown,
                                                                    contentDescription = "",
                                                                    tint = Gunmetal,
                                                                    modifier =  Modifier.size(20.dp)
                                                                )

                                                                val balanceTokenText = if (currentLang == "fa") convertEnglishDigitsToPersian("55.00") else "55.00"
                                                                Text(
                                                                    text = balanceTokenText,
                                                                    maxLines = 1,
                                                                    color = PoliceBlue,
                                                                    style = MaterialTheme.typography.bodyLarge,
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                )
                                                                Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                                                Image(painter = painterResource(id = R.drawable.vc_usdc),
                                                                    contentDescription = "",
                                                                    modifier =  Modifier.size(24.dp))
                                                            }

                                                            Row(
                                                                modifier = Modifier
                                                                    .fillMaxWidth(),
                                                                horizontalArrangement = Arrangement.End,
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Icon(
                                                                    painter = painterResource(id = R.drawable.vc_refresh),
                                                                    contentDescription = "",
                                                                    tint = Succes,
                                                                    modifier =  Modifier.size(20.dp)
                                                                )

                                                                Spacer(modifier = Modifier.width(screenWidth * 0.01f))

                                                                Text(
                                                                    text = "(${stringResource(id = R.string.app_name)})",
                                                                    maxLines = 1,
                                                                    color = Color(0xFF708AA7),
                                                                    style = MaterialTheme.typography.bodySmall,
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                )

                                                                Spacer(modifier = Modifier.width(screenWidth * 0.01f))

                                                                Text(
                                                                    text = stringResource(id = R.string.token_balance),
                                                                    maxLines = 1,
                                                                    color = Gunmetal,
                                                                    style = MaterialTheme.typography.labelSmall,
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                )

                                                                Spacer(modifier = Modifier.width(screenWidth * 0.02f))

                                                                Icon(
                                                                    painter = painterResource(id = R.drawable.vc_wallet),
                                                                    contentDescription = "",
                                                                    tint = Gunmetal,
                                                                    modifier =  Modifier.size(24.dp,screenHeight * 0.026f)
                                                                )
                                                            }
                                                        }
                                                        Row(
                                                            Modifier.fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Row(
                                                                Modifier
                                                                    .fillMaxWidth(0.5f),
                                                                horizontalArrangement = Arrangement.Start,
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {

                                                                val balanceTomanText = formatWithDots("250000",currentLang.toString())
                                                                Text(
                                                                    text = balanceTomanText,
                                                                    maxLines = 1,
                                                                    color = PoliceBlue,
                                                                    style = MaterialTheme.typography.bodyLarge,
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                )
                                                                Spacer(modifier = Modifier.width(screenWidth * 0.01f))
                                                                Text(text = "IRT",
                                                                    maxLines = 1,
                                                                    color = PoliceBlue,
                                                                    style = MaterialTheme.typography.bodyLarge,
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                )
                                                                Spacer(modifier = Modifier.width(screenWidth * 0.01f))
                                                                Text(text = if (currentLang == "fa"){
                                                                    convertEnglishDigitsToPersian("(50000)")
                                                                }else{
                                                                    "(50000)"
                                                                },
                                                                    maxLines = 1,
                                                                    color = Color(0xFF708AA7),
                                                                    style = MaterialTheme.typography.bodyLarge,
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                )
                                                            }
                                                            Row(
                                                                Modifier
                                                                    .fillMaxWidth(),
                                                                horizontalArrangement = Arrangement.End,
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Text(text = stringResource(id = R.string.balance_in_euros),
                                                                    maxLines = 1,
                                                                    color = Gunmetal,
                                                                    style = MaterialTheme.typography.labelSmall,
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                        .padding(end = 8.dp))

                                                                Icon(painter = painterResource(id = R.drawable.vc_coins),
                                                                    contentDescription = "",
                                                                    tint = Gunmetal,
                                                                    modifier =  Modifier.size(24.dp))
                                                            }
                                                        }
                                                    }
                                                }

                                        AnimatedVisibility(visible = !isVisibleHeader) {
                                                    Button(
                                                        onClick = {
                                                        isVisibleHeader = true
                                                    },
                                                        shape = RoundedCornerShape(8.dp),
                                                        contentPadding = PaddingValues(0.dp),
                                                        colors = ButtonDefaults. buttonColors(
                                                            contentColor = White
                                                        ),
                                                        modifier = Modifier.size(24.dp)) {
                                                        Icon(imageVector = Icons.Default.KeyboardArrowDown,
                                                            contentDescription = "",
                                                            tint = White,
                                                            modifier =  Modifier.size(20.dp))
                                                    }
                                                }

                                        Spacer(modifier = Modifier.height(screenHeight * 0.004f))
                                    }
                                }
                            }
                        }

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(screenHeight * 0.12f)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.vc_wave),
                                contentDescription = null,
//                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .fillMaxWidth()
//                                    .height(screenHeight * 0.048f)
                            )

                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl){
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .fillMaxWidth()
                                        .padding(
                                            top = screenHeight * 0.024f,
                                            bottom = screenHeight * 0.001f
                                        )
//                                    horizontalArrangement = Arrangement.spacedBy(60.dp,Alignment.CenterHorizontally)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(0.333f)
                                            .offset(x = 2.dp),
                                        verticalArrangement = Arrangement.spacedBy(screenHeight * 0.002f),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        FilledTonalButton(
                                            onClick = { },
                                            Modifier
                                                .size(screenHeight * 0.063f)
                                                .align(Alignment.CenterHorizontally),
                                            shape = RoundedCornerShape(32.dp),
                                            contentPadding =  PaddingValues(
                                                horizontal = 0.dp,
                                                vertical = 0.dp
                                            ),
                                            border =  BorderStroke(screenHeight * 0.008f, Background) ,
                                            colors =  ButtonDefaults.buttonColors(
                                                contentColor = NationsBlue
                                            )
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.vc_qrcode),
                                                contentDescription = "",
                                                modifier = Modifier.size(24.dp),
                                                tint = White)
                                        }
                                        Text(
                                            text = stringResource(id = R.string.payment),
                                            style = MaterialTheme.typography.bodyLarge,
                                            textAlign = TextAlign.Center,
                                            color = Gunmetal,

                                            )
                                    }
                                    Column(
                                        modifier = Modifier.fillMaxWidth(0.5f),
                                        verticalArrangement = Arrangement.spacedBy(screenHeight * 0.002f),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        FilledTonalButton(
                                            onClick = {
                                                onNavigateToTransaction()
                                            },
                                            Modifier
                                                .size(screenHeight * 0.063f)
                                                .align(Alignment.CenterHorizontally),
                                            shape = RoundedCornerShape(32.dp),
                                            contentPadding =  PaddingValues(
                                                horizontal = 0.dp,
                                                vertical = 0.dp
                                            ),
                                            border =  BorderStroke(screenHeight * 0.008f, Background) ,
                                            colors =  ButtonDefaults.buttonColors(
                                                contentColor = NationsBlue
                                            )
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.vc_arrows_exchange),
                                                contentDescription = "",
                                                modifier = Modifier.size(24.dp),
                                                tint = White)
                                        }
                                        Text(
                                            text = stringResource(id = R.string.transaction),
                                            style = MaterialTheme.typography.bodyLarge,
//                                            modifier = Modifier.width(74.dp),
                                            textAlign = TextAlign.Center,
                                            color = Gunmetal
                                        )
                                    }
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .offset(x = -2.dp),
                                        verticalArrangement = Arrangement.spacedBy(screenHeight * 0.002f),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        FilledTonalButton(
                                            onClick = {
                                                onNavigateToInvoice()
                                            },
                                            Modifier
                                                .size(screenHeight * 0.063f)
                                                .align(Alignment.CenterHorizontally),
                                            shape = RoundedCornerShape(32.dp),
                                            contentPadding =  PaddingValues(
                                                horizontal = 0.dp,
                                                vertical = 0.dp
                                            ),
                                            border =  BorderStroke(screenHeight * 0.008f, Background) ,
                                            colors =  ButtonDefaults.buttonColors(
                                                contentColor = NationsBlue
                                            )
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.vc_receipt),
                                                contentDescription = "",
                                                modifier = Modifier.size(screenHeight * 0.026f),
                                                tint = White)
                                        }
                                        Text(
                                            text = stringResource(id = R.string.invoice),
                                            style = MaterialTheme.typography.bodyLarge,
//                                            modifier = Modifier.width(75.dp),
                                            textAlign = TextAlign.Center,
                                            color = Gunmetal
                                        )
                                    }
                                }
                            }

                        }


                        Column(
                            Modifier
                                .fillMaxWidth()
                                .height(screenHeight * 0.2f)
                                .verticalScroll(scrollState)
                                .padding(top = screenHeight * 0.017f),
                            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.012f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                Modifier.fillMaxWidth(0.88f),
                                horizontalArrangement = Arrangement.spacedBy(14.dp,Alignment.End)
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        onNavigateToExchange()
                                    },
                                    modifier = Modifier
//                                        .size(112.dp,74.dp)
                                        .width(screenWidth * 0.27f)
                                        .height(screenHeight * 0.08f),
                                    shape = RoundedCornerShape(16.dp),
                                    border = BorderStroke(1.5.dp,Color(0xFFCEC0FF)),
                                    contentPadding = PaddingValues(
                                        horizontal = 2.dp,
                                        vertical = 2.dp
                                    )
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(2.dp,Alignment.CenterVertically),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(painter = painterResource(id = R.drawable.ic_bank),
                                            contentDescription = "",
                                            modifier = Modifier.size(screenHeight * 0.035f))
                                        Text(
                                            text = stringResource(id = R.string.exchange),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Gunmetal,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                                OutlinedButton(
                                    onClick = {
                                        onNavigateToFoodReservation()
                                    },
                                    modifier = Modifier
//                                        .size(112.dp,74.dp)
                                        .width(screenWidth * 0.27f)
                                        .height(screenHeight * 0.08f),
                                    shape = RoundedCornerShape(16.dp),
                                    contentPadding = PaddingValues(
                                        horizontal = 2.dp,
                                        vertical = 2.dp
                                    ),
                                    border = BorderStroke(1.5.dp,Color(0xFFFFC0C0))
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(2.dp,Alignment.CenterVertically),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(painter = painterResource(id = R.drawable.ic_fast_food),
                                            contentDescription = "",
                                            modifier = Modifier.size(screenHeight * 0.035f)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.food_reservation_exchange),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Gunmetal,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
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
fun StudentHomePreview() {
    SinarTheme {
        StudentHomeScreen()
    }
}