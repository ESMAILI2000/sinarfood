package com.enet.sinar.ui.view.staff

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.enet.sinar.ui.view.MenuItem
import com.enet.sinar.ui.view.TextFeildItem
import com.enet.sinar.ui.view.custom_view.DashedContainer
import com.enet.sinar.ui.view.custom_view.EllipsizedMiddleText
import com.enet.sinar.ui.view.custom_view.HexagonShape

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun StaffHomeScreen(
    onLogout: () -> Unit={},
    modifier: Modifier = Modifier) {

    val context = LocalContext.current
    var isVisibleHeader by remember { mutableStateOf(false) }
    var isVisibleFlotion by remember { mutableStateOf(false) }
    var isDialogSetting by remember { mutableStateOf(false) }
    var isDialogMenu by remember { mutableStateOf(false) }
    var isDialogEditProfile by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(false) }
    var pageCount =  4
    val listState = rememberLazyListState()
    val currentPage by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex
        }
    }

    val itemsSetting = listOf(
        MenuItem(stringResource(id = R.string.profile_settings), painterResource(id = R.drawable.vc_user_circle)),
        MenuItem(stringResource(id = R.string.fingerprint_settings), painterResource(id = R.drawable.vc_fingerprint)),
        MenuItem(stringResource(id = R.string.verification_levels), painterResource(id = R.drawable.vc_shield_lock)),
        MenuItem(stringResource(id = R.string.about_us), painterResource(id = R.drawable.vc_users)),
        MenuItem(stringResource(id = R.string.white_paper), painterResource(id = R.drawable.vc_notes))
    )

    val itemsMenu = listOf(
        MenuItem(stringResource(id = R.string.create_account), painterResource(id = R.drawable.vc_user_plus)),
        MenuItem(stringResource(id = R.string.add_network), painterResource(id = R.drawable.vc_topology_full)),
        MenuItem(stringResource(id = R.string.token_balances), painterResource(id = R.drawable.vc_wallet)),
        MenuItem(stringResource(id = R.string.roadmap), painterResource(id = R.drawable.vc_route)),
        MenuItem(stringResource(id = R.string.view_private_keys), painterResource(id = R.drawable.vc_eye)),
        MenuItem(stringResource(id = R.string.recover_accounts), painterResource(id = R.drawable.vc_rotate_clockwise)),
        MenuItem(stringResource(id = R.string.add_token), painterResource(id = R.drawable.vc_plus))
    )

    val itemsEditProfile = listOf(
        TextFeildItem("", painterResource(id = R.drawable.vc_user),mutableStateOf("")),
        TextFeildItem("", painterResource(id = R.drawable.vc_school),mutableStateOf(""), isNumeric = true),
        TextFeildItem("", painterResource(id = R.drawable.vc_user_circle),mutableStateOf("")),
        TextFeildItem("", painterResource(id = R.drawable.vc_phone_call),mutableStateOf(""), isNumeric = true),
        TextFeildItem(stringResource(id = R.string.new_password), painterResource(id = R.drawable.vc_lock),mutableStateOf(""), isPassword = true),
        TextFeildItem(stringResource(id = R.string.confirm_new_password), painterResource(id = R.drawable.vc_lock_open),mutableStateOf(""), isPassword = false),
    )

    val currentLang = MySharedPreferences.getLang(context)

    val layoutDirection = if (currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {

        BoxWithConstraints (
            modifier = Modifier
                .padding(
                    bottom = WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding() + 1.dp
                )
                .fillMaxSize()
        ) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.titleLarge,
                            color = Gunmetal
                        ) },
                        actions = {
                            Icon(
                                painter = painterResource(id = R.drawable.vc_settings),
                                contentDescription = null,
                                tint = Gunmetal,
                                modifier = Modifier
                                    .fillMaxWidth(0.06f)
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            isDialogSetting = true
                                        }
                                    })
                        },
                        navigationIcon = {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                tint = Gunmetal,
                                modifier = Modifier
                                    .fillMaxWidth(0.06f)
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
                        windowInsets = TopAppBarDefaults.windowInsets)
                },
                floatingActionButton = {

                    Box(modifier = Modifier){
                        AnimatedVisibility(visible = isVisibleFlotion,
                            enter = slideInVertically(
                                initialOffsetY = { fullHeight -> fullHeight }, // از پایین وارد شود
                                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                            ),
                            exit = slideOutVertically(
                                targetOffsetY = { fullHeight -> fullHeight }, // به پایین خارج شود
                                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                            )) {
                            FloatingActionButton(onClick = { /*TODO*/ },
                                shape = CircleShape,
                                contentColor = NationsBlue,
                                containerColor = White,
                                elevation = FloatingActionButtonDefaults.elevation(
                                    defaultElevation = 24.dp
                                ),
                                modifier = Modifier
                                    .size(32.dp)
                                    .offset(16.dp, -40.dp)
                            ) {
                                Icon(painter = painterResource(id = R.drawable.vc_blockchain),
                                    contentDescription = "",
                                    modifier = Modifier.size(screenWidth * 0.04f,screenHeight * 0.017f))
                            }
                        }
                        AnimatedVisibility(visible = isVisibleFlotion,
                            enter = slideInHorizontally(
                                initialOffsetX = { fullWidth -> fullWidth }, // از راست وارد شود
                                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                            ) + slideInVertically(
                                initialOffsetY = { fullHeight -> fullHeight }, // از پایین وارد شود
                                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                            ),
                            exit = slideOutHorizontally(
                                targetOffsetX = { fullWidth -> fullWidth }, // به راست خارج شود
                                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                            ) + slideOutVertically(
                                targetOffsetY = { fullHeight -> fullHeight }, // به پایین خارج شود
                                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                            )
                        ) {
                            FloatingActionButton(onClick = { /*TODO*/ },
                                shape = CircleShape,
                                contentColor = Color(0xFF8A38F5),
                                containerColor = White,
                                elevation = FloatingActionButtonDefaults.elevation(
                                    defaultElevation = 24.dp
                                ),
                                modifier = Modifier
                                    .size(32.dp)
                                    .offset(-24.dp, -24.dp)
                            ) {
                                Icon(painter = painterResource(id = R.drawable.vc_card),
                                    contentDescription = "",
                                    modifier = Modifier.size(screenWidth * 0.04f,screenHeight * 0.017f))
                            }
                        }
                        AnimatedVisibility(visible = isVisibleFlotion,
                            enter = slideInHorizontally(
                                initialOffsetX = { fullWidth -> fullWidth }, // از راست وارد شود
                                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                            ),
                            exit = slideOutHorizontally(
                                targetOffsetX = { fullWidth -> fullWidth }, // به راست خارج شود
                                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                            )) {
                            FloatingActionButton(onClick = { /*TODO*/ },
                                shape = CircleShape,
                                contentColor = Color(0xFF40D8AD),
                                containerColor = White,
                                elevation = FloatingActionButtonDefaults.elevation(
                                    defaultElevation = 24.dp
                                ),
                                modifier = Modifier
                                    .size(32.dp)
                                    .offset(-40.dp, 16.dp)
                            ) {
                                Icon(painter = painterResource(id = R.drawable.vc_list),
                                    contentDescription = "",
                                    modifier = Modifier.size(screenWidth * 0.04f,screenHeight * 0.017f))
                            }
                        }

                        AnimatedVisibility(visible = isVisibleFlotion,
                            enter = slideInHorizontally(
                                initialOffsetX = { fullWidth -> fullWidth }, // از راست وارد شود
                                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                            ) + slideInVertically(
                                initialOffsetY = { fullHeight -> -fullHeight }, // از بالا وارد شود
                                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                            ),
                            exit = slideOutHorizontally(
                                targetOffsetX = { fullWidth -> fullWidth }, // به راست خارج شود
                                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                            ) + slideOutVertically(
                                targetOffsetY = { fullHeight -> -fullHeight }, // به بالا خارج شود
                                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                            )) {
                            FloatingActionButton(onClick = { /*TODO*/ },
                                shape = CircleShape,
                                containerColor = White,
                                contentColor = Color(0xFFFF8E48),
                                elevation = FloatingActionButtonDefaults.elevation(
                                    defaultElevation = 24.dp
                                ),
                                modifier = Modifier
                                    .size(32.dp)
                                    .offset(-24.dp, 56.dp)
                            ) {
                                Icon(painter = painterResource(id = R.drawable.vc_lamp),
                                    contentDescription = "",
                                    modifier = Modifier.size(screenWidth * 0.04f,screenHeight * 0.017f))
                            }
                        }

                        FloatingActionButton(
                            onClick = {
                                isVisibleFlotion = !isVisibleFlotion
                            },
                            modifier = Modifier
                                .size(64.dp),
                            shape = RoundedCornerShape(32.dp),
                            contentColor = Color.Black,
                            containerColor = White,
                            elevation = FloatingActionButtonDefaults.elevation(
                                defaultElevation = 24.dp
                            )
                        ) {
                            Icon(painter = painterResource(id = if(isVisibleFlotion) R.drawable.vc_close else R.drawable.ic_basketball),
                                contentDescription = "",
                                modifier = Modifier.size(40.dp))
                        }
                    }

                },
                bottomBar = {
                    var selectedItem by remember { mutableStateOf(3) }
                    val items = listOf(
                        MenuItem(stringResource(id = R.string.my_miner), painterResource(id = R.drawable.vc_hammer_crash)),
                        MenuItem(stringResource(id = R.string.rothbard), painterResource(id = R.drawable.vc_student)),
                        MenuItem(stringResource(id = R.string.library), painterResource(id = R.drawable.vc_book_alt)),
                        MenuItem(stringResource(id = R.string.home), painterResource(id = R.drawable.vc_filled_home))
                    )

                    Box(
                        Modifier
                            .background(White)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center){
                        Box(modifier = Modifier
                            .padding(vertical = screenHeight * 0.017f)
                            .background(White)
                            .size(380.dp, 84.dp))
                        {
                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.9f)
                                .background(Background, RoundedCornerShape(16.dp))
                                .align(Alignment.BottomCenter))

                            Row(
                                Modifier
                                    .fillMaxWidth(0.9f)
                                    .align(Alignment.TopCenter)
                                    .fillMaxHeight(0.9f),
//                        verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Center) {
                                items.forEachIndexed { index, item ->

                                    Box(modifier = Modifier
                                        .align(Alignment.Top)
                                        .size(80.dp, 70.dp)){
                                        Spacer(modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(0.5f)
                                            .align(Alignment.TopCenter)
                                            .background(if (selectedItem == index) White else Color.Transparent))
                                        Spacer(modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(0.5f)
                                            .align(Alignment.BottomCenter))
                                        Spacer(modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .size(10.dp, 60.dp)
                                            .background(Background, RoundedCornerShape(topEnd = 32.dp)))

                                        Column(
                                            modifier = Modifier
                                                .align(if (selectedItem == index) Alignment.TopCenter else Alignment.BottomCenter)
                                                .width(60.dp)
                                                .height(60.dp)
//                                    .fillMaxHeight()
                                                .background(
                                                    if (selectedItem == index) White else Color.Transparent,
                                                    RoundedCornerShape(
                                                        topStart = 0.dp,
                                                        topEnd = 0.dp,
                                                        bottomEnd = 20.dp,
                                                        bottomStart = 20.dp
                                                    )
//                                            CircleShape
                                                )
                                                .pointerInput(Unit) {
                                                    detectTapGestures {
                                                        selectedItem = index
                                                    }
                                                },
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Top
                                        ) {
                                            Box(modifier = Modifier
                                                .size(48.dp, 48.dp)
                                                .background(
                                                    if (selectedItem == index) Color.Yellow else Background,
                                                    CircleShape
                                                ),
                                                contentAlignment = Alignment.Center
                                            ){
                                                Icon(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.06f),
                                                    painter = item.icon,
                                                    contentDescription = item.text,
                                                    tint = if (selectedItem == index) Color.Black else Water
                                                )
                                            }

                                            Spacer(modifier = Modifier.height(1.dp))
                                            if (selectedItem != index){
                                                Text(item.text, fontSize = 12.sp, color = Water)
                                            }
                                        }
                                        Spacer(modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .size(10.dp, 60.dp)
                                            .background(Background, RoundedCornerShape(topStart = 32.dp)))
                                    }

                                }

                            }
//                    BottomAppBar(
////                        contentColor = Color.Transparent,
//                        containerColor = Color.Transparent,
//                        modifier = Modifier.align(Alignment.TopCenter)
//                            .padding(horizontal = 16.dp)
//                    ) {
//                        items.forEachIndexed { index, item ->
//                            NavigationBarItem(
//                                modifier = Modifier
//                                    .background(
//                                        if (selectedItem == index) White else Color.Transparent,
//                                        RoundedCornerShape(topEnd = 0.dp, topStart = 0.dp, bottomEnd = 58.dp, bottomStart = 58.dp)
//                                    )
//                                    .size(77.dp,77.dp),
//                                selected = selectedItem == index,
//                                onClick = { selectedItem = index },
//                                icon = {
//                                    Box(
//                                        contentAlignment = Alignment.Center,
//                                        modifier = Modifier
//                                            .background(
//                                                if (selectedItem == index)
//                                                    Color(0xFFFFD84D) // رنگ زرد برای خانه
//                                                else
//                                                    Color.Transparent,
//                                                shape = CircleShape
//                                            )
//                                            .size(58.dp)
//                                            .padding(if (selectedItem == index) 8.dp else 0.dp)
//                                    ) {
//
//                                        Icon(
//                                            imageVector = item.icon,
//                                            contentDescription = item.label,
//                                            tint = if (selectedItem == index) Color.Black else Water
//                                        )
//                                    }
//                                },
//                                label = if (selectedItem != index) {
//                                    { Text(item.label, fontSize = 12.sp) }
//                                } else null,
//                                colors = NavigationBarItemDefaults. colors(
//                                    indicatorColor = Color.Transparent
//                                )
//                            )
//                        }
//
//                    }
                        }
                    }
                },
                floatingActionButtonPosition = FabPosition. End,
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .background(White),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){

                        if (isDialogMenu){
                            Column(
                                verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Dialog(
                                    onDismissRequest = {
                                        isDialogMenu = false
                                    },
                                    properties = DialogProperties(usePlatformDefaultWidth = false)
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
                                                    Modifier.width(24.dp),
                                                    thickness = 4.dp,
                                                    color = Gray04
                                                )
                                                itemsMenu.forEachIndexed { index, menuItem ->
                                                    Row(
                                                        modifier = Modifier
                                                            .size(332.dp, 60.dp)
                                                            .background(
                                                                Background,
                                                                RoundedCornerShape(16.dp)
                                                            ),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Box(
                                                            modifier = Modifier.fillMaxWidth(0.2f),
                                                            contentAlignment = Alignment.CenterStart){
                                                            Icon(
                                                                imageVector = if (currentLang == "fa") Icons.Default.KeyboardArrowLeft else Icons.Default.KeyboardArrowRight,
                                                                contentDescription = "" ,
                                                                tint = EerieBlack,
                                                                modifier = Modifier
                                                                    .padding(start = 24.dp)
                                                                    .size(20.dp)
                                                            )
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(start = 24.dp),
                                                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Icon(
                                                                painter = itemsMenu[index].icon ,
                                                                contentDescription = "",
                                                                tint = NationsBlue,
                                                                modifier = Modifier
                                                                    .fillMaxWidth(0.06f)
                                                            )
                                                            Text(text = itemsMenu[index].text,
                                                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                                                color = EerieBlack)
                                                        }
                                                    }
                                                }
                                                Spacer(modifier = Modifier.height(screenHeight * 0.009f))
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
                                            Icon(painter = painterResource(id = R.drawable.vc_close),
                                                contentDescription = "")
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
                                Dialog(
                                    onDismissRequest = {
                                        isDialogSetting = false
                                    },
                                    properties = DialogProperties(usePlatformDefaultWidth = false)
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
                                                    .width(24.dp)
                                                    .padding(top = screenHeight * 0.017f),
                                                thickness = 4.dp,
                                                color = Gray04
                                            )

                                            Box(modifier = Modifier
                                                .offset(y = 16.dp)
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
                                                        .fillMaxWidth(0.06f)
                                                        .align(Alignment.BottomCenter)
                                                        .border(
                                                            BorderStroke(1.dp, NationsBlue),
                                                            CircleShape
                                                        )
                                                        .background(White, CircleShape)
                                                        .padding(4.dp),
                                                    tint = NationsBlue)

                                            }

                                            Text(text = "ریحانه کشاورز حدادها",
                                                style = MaterialTheme.typography.displayLarge,
                                                modifier = Modifier
                                                    .padding(top = screenHeight * 0.017f),
                                                color = Gunmetal)
                                            Row(
                                                Modifier
                                                    .padding(top = screenHeight * 0.009f),
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(painter = painterResource(id = R.drawable.vc_school),
                                                    contentDescription = "",
                                                    tint = GrayF,
                                                    modifier =  Modifier.size(20.dp))

                                                Text(text = "43167487998",
                                                    maxLines = 1,
                                                    color = GrayF,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    textAlign = TextAlign.Start,
                                                    modifier = Modifier
                                                        .padding(start = 8.dp))
                                            }
                                            Column(
                                                modifier = Modifier.padding(top = screenHeight * 0.017f),
                                                verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f)
                                            ) {

                                                itemsSetting.forEachIndexed { index, menuItem ->
                                                    Row(
                                                        modifier = Modifier
                                                            .size(332.dp, 60.dp)
                                                            .background(
                                                                Background,
                                                                RoundedCornerShape(16.dp)
                                                            ),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Box(
                                                            modifier = Modifier.fillMaxWidth(0.5f),
                                                            contentAlignment = Alignment.CenterStart){
                                                            Icon(
                                                                imageVector = if (currentLang == "fa") Icons.Default.KeyboardArrowLeft else Icons.Default.KeyboardArrowRight,
                                                                contentDescription = "" ,
                                                                tint = EerieBlack,
                                                                modifier = Modifier
                                                                    .padding(start = 24.dp)
                                                                    .size(20.dp)
                                                            )
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(start = 24.dp),
                                                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Icon(
                                                                painter = itemsSetting[index].icon ,
                                                                contentDescription = "",
                                                                tint = NationsBlue,
                                                                modifier = Modifier
                                                                    .fillMaxWidth(0.06f)
                                                            )
                                                            Text(text = itemsSetting[index].text,
                                                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                                                color = EerieBlack)
                                                        }
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .size(332.dp, 60.dp)
                                                        .background(
                                                            Background,
                                                            RoundedCornerShape(16.dp)
                                                        ),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Row(
                                                        Modifier.padding(start = 24.dp),
                                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                                    ) {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.vc_cube_sphere) ,
                                                            contentDescription = "",
                                                            tint = NationsBlue,
                                                            modifier = Modifier
                                                                .fillMaxWidth(0.06f)
                                                        )
                                                        Text(text = stringResource(id = R.string.airdrop),
                                                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                                            color = EerieBlack)
                                                    }

                                                }

                                                Row(
                                                    modifier = Modifier
                                                        .size(332.dp, 60.dp)
                                                        .background(
                                                            Background,
                                                            RoundedCornerShape(16.dp)
                                                        ),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth(0.5f)
                                                            .padding(start = 24.dp),
                                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.vc_moon) ,
                                                            contentDescription = "",
                                                            tint = NationsBlue,
                                                            modifier = Modifier
                                                                .fillMaxWidth(0.06f)
                                                        )
                                                        Text(text = stringResource(id = R.string.app_theme),
                                                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                                            color = EerieBlack)
                                                    }

                                                    Box(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        contentAlignment = Alignment.CenterEnd){
                                                        Switch(
                                                            checked = isDarkMode,
                                                            onCheckedChange = {
                                                                isDarkMode = !isDarkMode
                                                            },
                                                            modifier = Modifier.padding(end = 20.dp),
                                                            enabled = true,
                                                            colors = SwitchDefaults. colors(
                                                                checkedTrackColor = Color(0xFF8BB9F2),
                                                                uncheckedTrackColor = Color(0xFF8BB9F2),
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
                                                        .size(332.dp, 60.dp)
                                                        .background(
                                                            Background,
                                                            RoundedCornerShape(16.dp)
                                                        ),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Row(
                                                        Modifier.padding(start = 24.dp),
                                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                                    ) {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.vc_power) ,
                                                            contentDescription = "",
                                                            tint = NationsBlue,
                                                            modifier = Modifier
                                                                .fillMaxWidth(0.06f)
                                                        )
                                                        Text(text = stringResource(id = R.string.logout),
                                                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                                            color = EerieBlack)
                                                    }

                                                }

                                                Spacer(modifier = Modifier.height(screenHeight * 0.009f))

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
                                            containerColor = White,

                                            ) {
                                            Icon(painter = painterResource(id = R.drawable.vc_close),
                                                contentDescription = "")
                                        }
                                        //
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
                                    },
                                    properties = DialogProperties(usePlatformDefaultWidth = false)
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
                                                    .width(24.dp)
                                                    .padding(top = screenHeight * 0.017f),
                                                thickness = 4.dp,
                                                color = Gray04
                                            )
                                            Icon(
                                                imageVector = if (currentLang == "fa") Icons.Default.ArrowBack else Icons.Default.ArrowForward,
                                                contentDescription = "" ,
                                                tint = EerieBlack,
                                                modifier = Modifier
                                                    .align(Alignment.Start)
                                                    .padding(start = screenWidth * 0.04f)
                                                    .fillMaxWidth(0.06f)
                                                    .pointerInput(Unit) {
                                                        detectTapGestures {
                                                            isDialogEditProfile = false
                                                        }
                                                    }
                                            )
                                            Column(
                                                modifier = Modifier.padding(top = screenHeight * 0.017f),
                                                verticalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                itemsEditProfile.forEachIndexed { index, menuItem ->
                                                    OutlinedTextField(
                                                        value = menuItem.value.value,
                                                        onValueChange = {
                                                            menuItem.value.value = it
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
                                                            unfocusedBorderColor = GrayE,
                                                            focusedBorderColor = NationsBlue,
                                                            errorBorderColor = Err,
                                                            focusedLeadingIconColor = NationsBlue,
                                                            unfocusedLeadingIconColor = GrayE,
                                                            errorLeadingIconColor = Err,
                                                            focusedTextColor = NationsBlue,
                                                            unfocusedTextColor = GrayE,
                                                            errorTextColor = Err,
                                                            focusedPlaceholderColor = NationsBlue,
                                                            unfocusedPlaceholderColor = GrayE,
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
                                                            .size(332.dp,56.dp)
                                                    )
                                                }

                                                Button(
                                                    onClick = {},
                                                    Modifier
                                                          .width(screenWidth * 0.8f)
                                    .height(screenHeight * 0.061f),
                                                    shape = RoundedCornerShape(12.dp),
                                                    colors = ButtonDefaults. buttonColors(
                                                        containerColor = NationsBlue,
                                                        contentColor = Color.White,
                                                    )
                                                ) {
                                                    Text(stringResource(id = R.string.save_changes),
                                                        fontFamily = FontFamily(Font(R.font.ir_medium)),
                                                        fontSize = 14.sp)
                                                    Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                                    Icon(painter = painterResource(id = R.drawable.vc_pencil) , contentDescription = null )
                                                }

                                                Spacer(modifier = Modifier.size(screenWidth * 0.04f,screenHeight * 0.017f))
                                            }
                                        }

                                        FloatingActionButton(
                                            onClick = {
                                                isDialogEditProfile = false
                                            },
                                            modifier = Modifier
                                                .size(56.dp),
                                            shape = CircleShape,
                                            contentColor = Color.Black,
                                            containerColor = White,

                                            ) {
                                            Icon(painter = painterResource(id = R.drawable.vc_close),
                                                contentDescription = "")
                                        }
                                        //
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
                            LazyRow(
                                state = listState,
                                flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
                                contentPadding = PaddingValues(horizontal = screenWidth * 0.058f),
                                horizontalArrangement = Arrangement.spacedBy(24.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                items(pageCount) { page ->
                                    DashedContainer(
                                        modifier = Modifier
                                            .padding(bottom = 8.dp, top = screenHeight * 0.017f)
                                            .fillMaxWidth(0.88f)
                                            .background(Color(0xFFD2EEFF), RoundedCornerShape(24.dp)),
                                        cornerRadius = 24.dp,
                                        borderColor = Color.Blue
                                    ) {
                                        Column(modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally) {
                                            Box(modifier = Modifier.fillMaxWidth()){
                                                Box(modifier = Modifier
                                                    .size(32.dp)
                                                    .offset(x = 24.dp, y = 24.dp)
                                                    .align(Alignment.TopStart)
                                                    .background(NationsBlue, CircleShape),
                                                    contentAlignment = Alignment.Center){
                                                    Icon(painter = painterResource(id = R.drawable.vc_infinity) ,
                                                        contentDescription ="" ,
                                                        modifier = Modifier.fillMaxWidth(0.06f),
                                                        tint = White)
                                                }
                                                Box(modifier = Modifier
                                                    .size(32.dp)
                                                    .offset(x = -24.dp, y = 24.dp)
                                                    .align(Alignment.TopEnd)
                                                    .background(NationsBlue, CircleShape),
                                                    contentAlignment = Alignment.Center){
                                                    Icon(painter = painterResource(id = R.drawable.vc_copy) ,
                                                        contentDescription ="" ,
                                                        modifier = Modifier.fillMaxWidth(0.06f),
                                                        tint = White)
                                                }
                                                Image(painter = painterResource(id = R.drawable.logo),
                                                    contentDescription ="",
                                                    modifier = Modifier
                                                        .align(Alignment.TopCenter)
                                                        .offset(y = 16.dp)
                                                        .size(57.dp, 64.dp)
                                                        .clip(HexagonShape()),
                                                    contentScale = ContentScale.Crop
                                                )
                                            }
                                            Text(text = "ریحانه کشاورز حدادها",
                                                style = MaterialTheme.typography.displayLarge,
                                                modifier = Modifier
                                                    .padding(top = screenHeight * 0.017f),
                                                color = Gunmetal)
                                            Row(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(top = screenHeight * 0.009f),
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(painter = painterResource(id = R.drawable.vc_copy),
                                                    contentDescription = "",
                                                    tint = Gunmetal,
                                                    modifier =  Modifier.size(20.dp))

                                                EllipsizedMiddleText(
                                                    text = "0x5A141B7eba38A151EDfcd816D912E6ae5C0307b1",
                                                    startLength = 10,
                                                    endLength = 5,
                                                    maxLines = 1,
                                                    color = Gunmetal,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    textAlign = TextAlign.End,
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.4f)
                                                        .padding(end = 8.dp))
                                            }

                                            Column(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(top = screenHeight * 0.009f),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                Row(
                                                    Modifier.fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(text = "4312440001",
                                                        maxLines = 1,
                                                        color = PoliceBlue,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        overflow = TextOverflow.Visible,
                                                        modifier = Modifier.fillMaxWidth(0.5f))
                                                    Row(
                                                        Modifier
                                                            .fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.End,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Text(text = stringResource(id = R.string.student_staff_id),
                                                            maxLines = 1,
                                                            color = Gunmetal,
                                                            style = MaterialTheme.typography.displayLarge.copy(fontSize = 12.sp),
                                                            overflow = TextOverflow.Visible,
                                                            modifier = Modifier
                                                                .padding(end = 8.dp))

                                                        Icon(painter = painterResource(id = R.drawable.vc_school),
                                                            contentDescription = "",
                                                            tint = Gunmetal,
                                                            modifier =  Modifier.fillMaxWidth(0.06f))
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
                                                        Icon(imageVector = Icons.Default.KeyboardArrowDown,
                                                            contentDescription = "",
                                                            tint = Gunmetal,
                                                            modifier =  Modifier.size(20.dp))

                                                        Text(text = "شبکه اکباتان",
                                                            maxLines = 1,
                                                            color = PoliceBlue,
                                                            style = MaterialTheme.typography.bodyLarge,
                                                            overflow = TextOverflow.Visible,
                                                            modifier = Modifier
                                                                .padding(end = 8.dp))

                                                        Image(painter = painterResource(id = R.drawable.vc_bnb),
                                                            contentDescription = "",
                                                            modifier =  Modifier.fillMaxWidth(0.06f))
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
                                                            style = MaterialTheme.typography.displayLarge.copy(fontSize = 12.sp),
                                                            overflow = TextOverflow.Visible,
                                                            modifier = Modifier
                                                                .padding(end = 8.dp))

                                                        Icon(painter = painterResource(id = R.drawable.vc_topology_ring),
                                                            contentDescription = "",
                                                            tint = Gunmetal,
                                                            modifier =  Modifier.fillMaxWidth(0.06f))
                                                    }
                                                }

                                                AnimatedVisibility(visible = isVisibleHeader) {
                                                    Column(
                                                        Modifier
                                                            .fillMaxWidth(),
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
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
                                                                Icon(imageVector = Icons.Default.KeyboardArrowDown,
                                                                    contentDescription = "",
                                                                    tint = Gunmetal,
                                                                    modifier =  Modifier.size(20.dp))

                                                                Text(text = "5500",
                                                                    maxLines = 1,
                                                                    color = PoliceBlue,
                                                                    style = MaterialTheme.typography.bodyLarge,
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                        .padding(end = 8.dp))

                                                                Image(painter = painterResource(id = R.drawable.vc_usdc),
                                                                    contentDescription = "",
                                                                    modifier =  Modifier.fillMaxWidth(0.06f))
                                                            }
                                                            Row(
                                                                Modifier
                                                                    .fillMaxWidth(),
                                                                horizontalArrangement = Arrangement.End,
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Icon(painter = painterResource(id = R.drawable.vc_refresh),
                                                                    contentDescription = "",
                                                                    tint = Succes,
                                                                    modifier =  Modifier.size(14.dp))

                                                                Text(text = "(${stringResource(id = R.string.app_name
                                                                )})",
                                                                    maxLines = 1,
                                                                    color = Color(0xFF708AA7),
                                                                    style = MaterialTheme.typography.bodySmall,
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                        .padding(horizontal = 4.dp))

                                                                Text(text = stringResource(id = R.string.token_balance),
                                                                    maxLines = 1,
                                                                    color = Gunmetal,
                                                                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 12.sp),
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                        .padding(end = 8.dp))

                                                                Icon(painter = painterResource(id = R.drawable.vc_wallet),
                                                                    contentDescription = "",
                                                                    tint = Gunmetal,
                                                                    modifier =  Modifier.fillMaxWidth(0.06f))
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
                                                                Text(text = "250000",
                                                                    maxLines = 1,
                                                                    color = PoliceBlue,
                                                                    style = MaterialTheme.typography.bodyLarge,
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                        .padding(end = screenWidth * 0.01f))

                                                                Text(text = "IRT",
                                                                    maxLines = 1,
                                                                    color = PoliceBlue,
                                                                    style = MaterialTheme.typography.bodyLarge,
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                        .padding(end = screenWidth * 0.01f))

                                                                Text(text = "(50000)",
                                                                    maxLines = 1,
                                                                    color = Color(0xFF708AA7),
                                                                    style = MaterialTheme.typography.bodyLarge,
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                        .padding(end = screenWidth * 0.01f))
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
                                                                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 12.sp),
                                                                    overflow = TextOverflow.Visible,
                                                                    modifier = Modifier
                                                                        .padding(end = 8.dp))

                                                                Icon(painter = painterResource(id = R.drawable.vc_coins),
                                                                    contentDescription = "",
                                                                    tint = Gunmetal,
                                                                    modifier =  Modifier.fillMaxWidth(0.06f))
                                                            }
                                                        }
                                                    }

                                                }

                                                AnimatedVisibility(visible = !isVisibleHeader) {
                                                    Button(onClick = {
                                                        isVisibleHeader = true
                                                    },
                                                        shape = RoundedCornerShape(12.dp),
                                                        contentPadding = PaddingValues(0.dp),
                                                        colors = ButtonDefaults. buttonColors(
                                                            contentColor = White
                                                        ),
                                                        modifier = Modifier.size(32.dp)) {
                                                        Icon(imageVector = Icons.Default.KeyboardArrowDown,
                                                            contentDescription = "",
                                                            tint = White,
                                                            modifier =  Modifier.size(20.dp))
                                                    }
                                                }


                                            }


                                        }


                                    }
                                }

                            }


                            Box(modifier = Modifier
                                .padding(top = screenHeight * 0.009f)
                                .background(White, RoundedCornerShape(32.dp)),
                                contentAlignment = Alignment.Center
                            ){
                                Row(
                                    Modifier
                                        .padding(6.dp),
                                    Arrangement.spacedBy(6.dp),
                                    Alignment.CenterVertically
                                ) { repeat(pageCount) { page ->
                                    Spacer(modifier = Modifier
                                        .size(if (page == currentPage) 8.dp else 6.dp)
                                        .background(
                                            if (page == currentPage) NationsBlue else Color(0xFFE6EAF3),
                                            CircleShape
                                        ))
                                }
                                }
                            }

                        }


                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(105.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.vc_wave),
                                contentDescription = null,
                                Modifier
                                    .align(Alignment.TopCenter)
                                    .fillMaxWidth()
                            )
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl){
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(60.dp,Alignment.CenterHorizontally)
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(2.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        FilledTonalButton(
                                            onClick = { /*TODO*/ },
                                            Modifier
                                                .size(58.dp)
                                                .align(Alignment.CenterHorizontally),
                                            shape = RoundedCornerShape(32.dp),
                                            contentPadding =  PaddingValues(
                                                horizontal = 0.dp,
                                                vertical = 0.dp
                                            ),
                                            border =  BorderStroke(8.dp, Background) ,
                                            colors =  ButtonDefaults.buttonColors(
                                                contentColor = NationsBlue
                                            )
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.vc_receipt),
                                                contentDescription = "",
                                                modifier = Modifier.fillMaxWidth(0.06f),
                                                tint = White)
                                        }
                                        Text(
                                            text = stringResource(id = R.string.invoice),
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.width(75.dp),
                                            textAlign = TextAlign.Center,
                                            color = Gunmetal
                                        )
                                    }
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(2.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        FilledTonalButton(
                                            onClick = { /*TODO*/ },
                                            Modifier
                                                .size(58.dp)
                                                .align(Alignment.CenterHorizontally),
                                            shape = RoundedCornerShape(32.dp),
                                            contentPadding =  PaddingValues(
                                                horizontal = 0.dp,
                                                vertical = 0.dp
                                            ),
                                            border =  BorderStroke(8.dp, Background) ,
                                            colors =  ButtonDefaults.buttonColors(
                                                contentColor = NationsBlue
                                            )
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.vc_arrows_exchange),
                                                contentDescription = "",
                                                modifier = Modifier.fillMaxWidth(0.06f),
                                                tint = White)
                                        }
                                        Text(
                                            text = stringResource(id = R.string.transaction),
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.width(74.dp),
                                            textAlign = TextAlign.Center,
                                            color = Gunmetal
                                        )
                                    }
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(2.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        FilledTonalButton(
                                            onClick = { /*TODO*/ },
                                            Modifier
                                                .size(58.dp)
                                                .align(Alignment.CenterHorizontally),
                                            shape = RoundedCornerShape(32.dp),
                                            contentPadding =  PaddingValues(
                                                horizontal = 0.dp,
                                                vertical = 0.dp
                                            ),
                                            border =  BorderStroke(8.dp, Background) ,
                                            colors =  ButtonDefaults.buttonColors(
                                                contentColor = NationsBlue
                                            )
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.vc_qrcode),
                                                contentDescription = "",
                                                modifier = Modifier.fillMaxWidth(0.06f),
                                                tint = White)
                                        }
                                        Text(
                                            text = stringResource(id = R.string.payment),
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.width(75.dp),
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
                                .padding(top = screenHeight * 0.017f),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                Modifier.fillMaxWidth(0.88f),
                                horizontalArrangement = Arrangement.spacedBy(14.dp,Alignment.CenterHorizontally)
                            ) {
                                OutlinedButton(
                                    onClick = { /*TODO*/ },
                                    modifier = Modifier.size(112.dp,74.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    border = BorderStroke(1.5.dp,Color(0xFFCEC0FF))
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(2.dp,Alignment.CenterVertically),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(painter = painterResource(id = R.drawable.ic_bank),
                                            contentDescription = "",
                                            modifier = Modifier.size(32.dp))
                                        Text(
                                            text = stringResource(id = R.string.exchange),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Gunmetal
                                        )
                                    }
                                }
                                OutlinedButton(
                                    onClick = { /*TODO*/ },
                                    modifier = Modifier.size(112.dp,74.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    border = BorderStroke(1.5.dp,Color(0xFFFFC0E8))
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(2.dp,Alignment.CenterVertically),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(painter = painterResource(id = R.drawable.ic_billboard),
                                            contentDescription = "",
                                            modifier = Modifier.size(32.dp)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.events),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Gunmetal
                                        )
                                    }
                                }
                                OutlinedButton(
                                    onClick = { /*TODO*/ },
                                    modifier = Modifier.size(112.dp,74.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    border = BorderStroke(1.5.dp,Color(0xFFC0FFC7))
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(2.dp,Alignment.CenterVertically),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(painter = painterResource(id = R.drawable.ic_inventory_management),
                                            contentDescription = "",
                                            modifier = Modifier.size(32.dp)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.asset_management),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Gunmetal
                                        )
                                    }
                                }
                            }
                            Row(
                                Modifier.fillMaxWidth(0.88f),
                                horizontalArrangement = Arrangement.spacedBy(14.dp,Alignment.End)
                            ) {
                                OutlinedButton(
                                    onClick = { /*TODO*/ },
                                    modifier = Modifier.size(112.dp,74.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    border = BorderStroke(1.5.dp,Color(0xFFFFCFC0))
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(2.dp,Alignment.CenterVertically),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(painter = painterResource(id = R.drawable.ic_distributed),
                                            contentDescription = "",
                                            modifier = Modifier.size(32.dp)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.tree_network),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Gunmetal
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
fun StaffHomePreview() {
    SinarTheme {
        StaffHomeScreen()
    }
}