package com.enet.sinar.ui.view.student.food

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.enet.sinar.R
import com.enet.sinar.ui.theme.Background
import com.enet.sinar.ui.theme.EerieBlack
import com.enet.sinar.ui.theme.Err
import com.enet.sinar.ui.theme.GargoyleGas
import com.enet.sinar.ui.theme.Gray04
import com.enet.sinar.ui.theme.GrayB
import com.enet.sinar.ui.theme.GrayC
import com.enet.sinar.ui.theme.GrayE
import com.enet.sinar.ui.theme.GrayF
import com.enet.sinar.ui.theme.Green
import com.enet.sinar.ui.theme.Gunmetal
import com.enet.sinar.ui.theme.NationsBlue
import com.enet.sinar.ui.theme.PoliceBlue
import com.enet.sinar.ui.theme.SinarTheme
import com.enet.sinar.ui.theme.Succes
import com.enet.sinar.ui.theme.Water
import com.enet.sinar.ui.theme.White
import com.enet.sinar.ui.utility.MySharedPreferences
import com.enet.sinar.ui.utility.MySharedPreferences.getFoodType
import com.enet.sinar.ui.utility.MySharedPreferences.getPickupLocation
import com.enet.sinar.ui.utility.MySharedPreferences.setFoodType
import com.enet.sinar.ui.utility.MySharedPreferences.setPickupLocation
import com.enet.sinar.ui.view.MenuItem
import com.enet.sinar.ui.view.convertEnglishDigitsToPersian
import com.enet.sinar.ui.view.formatWithDots
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationFoodScreen(
    modifier: Modifier = Modifier,
    onNavigateToBack: () -> Unit = {}
){

    val context = LocalContext.current
    var isReserv by remember { mutableStateOf(false) }
    var foodType by rememberSaveable { mutableStateOf("") }
    var pickupLocation by rememberSaveable { mutableStateOf("") }
    var isDialogRules by remember { mutableStateOf(false) } // وضعیت نمایش دیالوگ قوانین و مقررات
    var sheetValueState by remember { mutableStateOf(0) } //0-> شیت بسته 1-> شیت افزودن غذا  2-> شیت انتخاب غذا 3-> شیت انتخاب سلف

    val scope = rememberCoroutineScope()
    val state = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = false
        )
    )

    var isVisibleItem = remember {
        mutableStateListOf(false, false, false, false)
    }
    val currentLang = MySharedPreferences.getLang(context)

    val itemsFoods = if (currentLang == "fa") listOf(
        FoodItem("قرمه سبزی","سلف مرکزی",false, painterResource(id = R.drawable.ic_ghorme), color = Color(0xFFFF383C)),
        FoodItem("چلوکباب","سلف هنر و معماری",true, painterResource(id = R.drawable.ic_kabab),color = Color(0xFF34C759)),
        FoodItem("چلوکباب","سلف علوم پایه",true, painterResource(id = R.drawable.ic_kabab),color = Color(0xFF0088FF)),
    ) else listOf(
        FoodItem("Ghormeh Sabzi","Central Faculty",false, painterResource(id = R.drawable.ic_ghorme), color = Color(0xFFFF383C)),
        FoodItem("Chelo Kabab","Faculty of Architecture",true, painterResource(id = R.drawable.ic_kabab), color = Color(0xFF34C759)),
        FoodItem("Ghormeh Sabzi","Central Faculty",true, painterResource(id = R.drawable.ic_ghorme), color = Color(0xFF0088FF))
        )


    val layoutDirection = if (currentLang == "fa") LayoutDirection.Ltr else LayoutDirection.Rtl

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {

        BoxWithConstraints (
            modifier = Modifier
                .fillMaxSize()
        ) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            BottomSheetScaffold(
                scaffoldState = state ,
                sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                containerColor = White,
                contentColor = Gray04,
                sheetMaxWidth = 500.dp,
                sheetPeekHeight = if (sheetValueState == 0 || sheetValueState == 1) 0.dp else if (sheetValueState == 2) 412.dp else 260.dp,
                sheetTonalElevation = 16.dp,
                sheetSwipeEnabled = true,
                sheetDragHandle = {
                },
                sheetContent = {
                    if (sheetValueState == 1){
                        SheetValueAddFood(
                            lang = currentLang.toString(),
                            onSelectFoodType = {
                                sheetValueState = 2
                                scope.launch {  state.bottomSheetState.partialExpand() }
                            },
                            onSelectPickupLocation = {
                                sheetValueState = 3
                                scope.launch {  state.bottomSheetState.partialExpand() }
                            }
                        )
                    } else if ( sheetValueState == 2){
                        SheetValueSelectFoodType(
                            isExpanded = state.bottomSheetState.currentValue == SheetValue.Expanded,
                            currentLang = currentLang.toString(),
                            onClose = {
                                sheetValueState = 1
                                scope.launch {  state.bottomSheetState.expand() }
                            }
                        )
                    } else if (sheetValueState == 3){
                        SheetValueSelectPickupLocation(
                            isExpanded = state.bottomSheetState.currentValue == SheetValue.Expanded,
                            currentLang = currentLang.toString(),
                            onClose = {
                                sheetValueState = 1
                                scope.launch {  state.bottomSheetState.expand() }
                            }
                        )
                    }
                }
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                ) {
                    if (isDialogRules){
                        Column(
                            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Dialog(
                                onDismissRequest = {
                                    isDialogRules = false
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
                                            .height(screenHeight * 0.523f)
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
                                                .align(Alignment.Start)
                                                .padding(start = screenWidth * 0.04f)
                                                .size(24.dp)
                                                .pointerInput(Unit) {
                                                    detectTapGestures {
                                                        isDialogRules = false
                                                    }
                                                }
                                        )
                                        Box(
                                            modifier = Modifier
                                                .size(screenWidth * 0.8f, screenHeight * 0.436f)
                                                .padding(top = screenHeight * 0.017f)
                                                .border(
                                                    1.dp,
                                                    Color(0xFFA1A2A6),
                                                    RoundedCornerShape(12.dp)
                                                )
                                                .clip(RoundedCornerShape(12.dp)),
                                        ) {
                                            Text(
                                                text = ":قوانین و مقررات",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color(0xFF000000),
                                                modifier = Modifier
                                                    .padding(horizontal = 15.dp, vertical = 15.dp)
                                                    .align(Alignment.TopEnd)
                                            )
                                        }
                                    }

                                    FloatingActionButton(
                                        onClick = {
                                            isDialogRules = false
                                        },
                                        modifier = Modifier
                                            .size(56.dp),
                                        shape = CircleShape,
                                        contentColor = Color.Black,
                                        containerColor = White,

                                        ) {
                                        Icon(painter = painterResource(id = R.drawable.vc_close),
                                            contentDescription = "",
                                            modifier = Modifier.size(24.dp))
                                    }
                                    //
                                }
                            }
                        }
                    }

                    Column {
                        CompositionLocalProvider(LocalLayoutDirection provides if (currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr) {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.food_sale_reservation),
                                        style = MaterialTheme.typography.titleMedium,
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
                                .background(
                                    White,
                                    RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f)
                        ){
                            Spacer(modifier = Modifier.height(screenHeight * 0.026f))
                            Box(modifier = Modifier
                                .border(1.dp, GrayC, RoundedCornerShape(12.dp))
                                .fillMaxWidth(0.883f)
                                .height(56.dp)
                            ){
                                Text(text = stringResource(id = R.string.change_reservation_mode),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = PoliceBlue,
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .offset(x = -screenWidth * 0.03f))

                                Switch(
                                    checked =  isReserv ,
                                    onCheckedChange = {
                                        isReserv = !isReserv
                                    },
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .width(screenWidth * 0.1f)
                                        .height(screenHeight * 0.026f)
//                                        .size(48.dp, 24.dp)
                                        .offset(x = if (screenWidth >= 390.dp) screenWidth * 0.03f else 0.dp),
                                    enabled = true,
                                    colors = SwitchDefaults. colors(
                                        checkedTrackColor = Green,
                                        uncheckedTrackColor = GrayB,
                                        checkedThumbColor = White,
                                        uncheckedThumbColor = White,
                                        checkedBorderColor = Color.Transparent,
                                        uncheckedBorderColor = Color.Transparent,
                                    )
                                )
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .offset(x = screenWidth * 0.06f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(11.dp)
                                ) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.vc_policy),
                                        contentDescription = "",
                                        tint = Gunmetal,
                                        modifier = Modifier
                                            .size(30.dp)
//                                            .height(screenHeight * 0.03f)
                                            .pointerInput(Unit) {
                                                detectTapGestures {
                                                    isDialogRules = true
                                                }
                                            }
                                    )
                                    Box(
                                        modifier = Modifier
                                            .width(1.dp) // ضخامت خط
                                            .height(screenHeight * 0.03f) // ارتفاع خط
                                            .background(
                                                brush = Brush.verticalGradient(
                                                    colorStops = arrayOf(
                                                        0.0f to Color(0xFF66666600),     // بالا خاکستری
                                                        0.5f to Color(0xFF000000BA).copy(alpha = 0.73f),    // وسط مشکی
                                                        1.0f to Color(0xFF66666600)      // پایین خاکستری
                                                    )
                                                )
                                            )
                                    )

                                    Icon(
                                        painter = painterResource(id = R.drawable.vc_alert),
                                        contentDescription = "",
                                        tint = Gunmetal,
                                        modifier = Modifier
                                            .size(30.dp)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .width(1.dp) // ضخامت خط
                                            .height(screenHeight * 0.03f) // ارتفاع خط
                                            .background(
                                                brush = Brush.verticalGradient(
                                                    colorStops = arrayOf(
                                                        0.0f to Color(0xFF66666600),     // بالا خاکستری
                                                        0.5f to Color(0xFF000000BA).copy(alpha = 0.73f),    // وسط مشکی
                                                        1.0f to Color(0xFF66666600)      // پایین خاکستری
                                                    )
                                                )
                                            )
                                    )
                                }

                            }
                            LazyColumn(
                                modifier = Modifier
//                                    .width(364.dp)
                                    .width(screenWidth * 0.883f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f)
                            )
                            { items(itemsFoods.size) { page ->
                                Box(modifier = Modifier
//                                    .width(364.dp)
                                    .fillMaxWidth()
                                    .background(
                                        if (itemsFoods[page].isMen) Succes else GargoyleGas,
                                        RoundedCornerShape(16.dp)
                                    )
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            isVisibleItem[page] = true
                                        }
                                    }
                                ){
                                    Column(
                                        modifier = Modifier
//                                            .width(362.dp)
                                            .fillMaxWidth(0.994f)
                                            .align(Alignment.CenterEnd)
                                            .background(White, RoundedCornerShape(16.dp))
                                            .clip(RoundedCornerShape(16.dp))
                                    ) {
                                        Box(modifier = Modifier
                                            .width(screenWidth * 0.88f)
                                            .height(screenHeight * 0.091f)
                                        ){
                                            Box(modifier = Modifier
                                                .size(64.dp)
                                                .align(Alignment.CenterEnd)
                                                .offset(x = -screenWidth * 0.039f)
                                                .clip(RoundedCornerShape(16.dp)),
                                                contentAlignment = Alignment.Center){
                                                Image(
                                                    painter = itemsFoods[page].image,
                                                    contentDescription = "",
//                                                    alpha = 0.64f,
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .size(64.dp)
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .matchParentSize()
                                                        .background(Color.Black.copy(alpha = 0.5f)) // مقدار alpha رو می‌تونی تنظیم کنی
                                                )
                                                Icon(
                                                    painter = painterResource(id = when(itemsFoods[page].isMen){
                                                        true -> R.drawable.vc_man
                                                        false -> R.drawable.vc_women
                                                    }),
                                                    contentDescription = "",
                                                    tint = White,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }

                                            Column(
                                                modifier = Modifier
                                                    .width(screenWidth * 0.33f)
                                                    .align(Alignment.CenterEnd)
                                                    .offset(x = -screenWidth * 0.223f),
                                                horizontalAlignment = Alignment.End
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.End,
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Text(text = itemsFoods[page].resturanName,
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = itemsFoods[page].color
                                                    )
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.vc_building_community),
                                                        contentDescription = "",
                                                        tint = GrayF,
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }
                                                Text(text = itemsFoods[page].foodName,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    color = Gunmetal
                                                )
                                            }

                                            Column(
                                                modifier = Modifier
                                                    .width(screenWidth * 0.31f)
                                                    .align(Alignment.CenterStart)
                                                    .offset(x = screenWidth * 0.022f),
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                                ) {
                                                    val amountAvsinText = if (currentLang == "fa") convertEnglishDigitsToPersian("20") else "20"
                                                    Text(text = amountAvsinText,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = PoliceBlue
                                                    )
                                                    Text(text = "avsin",
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = PoliceBlue
                                                    )
                                                }
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                                ) {
                                                    Text(text = stringResource(id = R.string.toman),
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = GrayF
                                                    )
                                                    val amountTomanText = formatWithDots("10000",currentLang.toString()) // اضافه کردن نقطه بعد از 3 رقم
                                                    Text(text = amountTomanText,
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = GrayF
                                                    )
                                                }
                                            }

                                        }

                                        Column(
                                            modifier = Modifier
                                                .width(screenWidth * 0.8f)
                                                .align(Alignment.CenterHorizontally)
                                        ) {
                                            AnimatedVisibility(visible = isVisibleItem[page],
                                                enter = slideInVertically(
                                                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                                                ),
                                                exit = slideOutVertically(
                                                    targetOffsetY = { fullHeight -> -fullHeight }, // به بالا خارج شود
                                                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                                                )
                                            ) {
                                                Column(
                                                    verticalArrangement = Arrangement.spacedBy(screenHeight * 0.017f)
                                                ) {
                                                    Spacer(modifier = Modifier.height(screenHeight * 0.001f))
                                                    HorizontalDivider(
                                                        Modifier
                                                            .fillMaxWidth(),
                                                        thickness = 1.dp,
                                                        color = GrayB
                                                    )

                                                    Box(modifier = Modifier
                                                        .fillMaxWidth()
                                                    ){
                                                        Box(modifier = Modifier
                                                            .size(30.dp)
                                                            .background(
                                                                NationsBlue.copy(alpha = 0.1f),
                                                                RoundedCornerShape(8.dp)
                                                            ),
                                                            contentAlignment = Alignment.Center
                                                        ){
                                                            Icon(
                                                                painter = painterResource(id = R.drawable.vc_settings),
                                                                contentDescription = "",
                                                                tint = NationsBlue,
                                                                modifier = Modifier.size(18.dp)
                                                            )
                                                        }

                                                        Box(modifier = Modifier
                                                            .size(30.dp)
                                                            .offset(x = 38.dp)
                                                            .background(
                                                                Err.copy(alpha = 0.1f),
                                                                RoundedCornerShape(8.dp)
                                                            ),
                                                            contentAlignment = Alignment.Center
                                                        ){
                                                            Icon(
                                                                painter = painterResource(id = R.drawable.vc_trash),
                                                                contentDescription = "",
                                                                tint = Err,
                                                                modifier = Modifier.size(18.dp)
                                                            )
                                                        }

                                                        Row(modifier = Modifier
                                                            .width(screenWidth * 0.22f)
                                                            .height(30.dp)
//                                                        .size(90.dp, 30.dp)
                                                            .offset(x = screenWidth * 0.2f)
                                                            .border(
                                                                1.dp,
                                                                PoliceBlue,
                                                                RoundedCornerShape(8.dp)
                                                            ),
                                                            horizontalArrangement = Arrangement.Center,
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Icon(
                                                                painter = painterResource(id = R.drawable.vc_clock_hour),
                                                                contentDescription = "",
                                                                tint = PoliceBlue,
                                                                modifier = Modifier
                                                                    .size(18.dp)
                                                                    .offset(x = -screenWidth * 0.004f)
                                                            )
                                                            val timeText = if (currentLang == "fa") convertEnglishDigitsToPersian("01:21:30") else "01:21:30"
                                                            Text(text = timeText,
                                                                style = MaterialTheme.typography.bodySmall,
                                                                color = PoliceBlue
                                                            )
                                                        }

                                                        Row(modifier = Modifier
                                                            .width(screenWidth * 0.26f)
                                                            .height(30.dp)
//                                                        .size(106.dp, 30.dp)
                                                            .align(Alignment.CenterEnd)
                                                            .background(
                                                                PoliceBlue.copy(alpha = 0.1f),
                                                                RoundedCornerShape(8.dp)
                                                            ),
                                                            horizontalArrangement = Arrangement.Center,
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            val qrCodeText = if (currentLang == "fa") convertEnglishDigitsToPersian("58947664") else "58947664"
                                                            Text(text = qrCodeText,
                                                                style = MaterialTheme.typography.bodySmall,
                                                                color = PoliceBlue
                                                            )
                                                            Icon(
                                                                painter = painterResource(id = R.drawable.vc_qrcode),
                                                                contentDescription = "",
                                                                tint = PoliceBlue,
                                                                modifier = Modifier
                                                                    .size(18.dp)
                                                                    .offset(x = screenWidth * 0.004f)
                                                            )
                                                        }
                                                    }

                                                    Button(
                                                        onClick = {},
                                                        Modifier
                                                            .align(Alignment.CenterHorizontally)
//                                                        .size(332.dp, 48.dp)
                                                            .width(screenWidth * 0.8f)
                                                            .height(48.dp),
                                                        shape = RoundedCornerShape(12.dp),
                                                        colors = ButtonDefaults. buttonColors(
                                                            containerColor = NationsBlue,
                                                            contentColor = Color.White,
                                                        )
                                                    ) {
                                                        Text(stringResource(id = R.string.purchase_food),
                                                            fontFamily = FontFamily(Font(R.font.ir_medium)),
                                                            fontSize = 14.sp)
                                                        Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                                                        Icon(imageVector = Icons.Default.ShoppingCart , contentDescription = null )
                                                    }
                                                    Spacer(modifier = Modifier.size(1.dp))
                                                }

                                            }
                                        }

                                    }

                                }
                            }

                            }
                        }
                    }


                    FloatingActionButton(
                        onClick = {
                            scope.launch {  state.bottomSheetState.expand() }
                            sheetValueState = 1
                        },
                        shape = RoundedCornerShape(68.dp),
                        contentColor = White,
                        containerColor = NationsBlue,
                        elevation = FloatingActionButtonDefaults.elevation(
                            defaultElevation = 24.dp
                        ),
                        modifier = Modifier
                            .width(screenWidth * 0.32f)
                            .height(52.dp)
//                            .size(132.dp, 52.dp)
                            .align(Alignment.BottomCenter)
                            .offset(y = -screenHeight * 0.017f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = stringResource(id = R.string.add_food),
                                style = MaterialTheme.typography.bodyLarge,
                                color = White
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Icon(painter = painterResource(id = R.drawable.vc_salad),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp))
                        }

                    }
                }

                // لایه نیمه‌شفاف وقتی شیت بازه
                if (sheetValueState != 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    scope.launch {
                                        state.bottomSheetState.partialExpand()
                                        sheetValueState = 0
                                    }
                                }
                            } // جلوگیری از تعامل با محتوای اصلی صفحه
                    )
                }
            }
        }

        }

    }

    @Composable
    fun SheetValueAddFood(
        viewModel: Foodviewmodel = viewModel(),
        onSelectFoodType: () -> Unit={},
        onSelectPickupLocation: () -> Unit={},
        lang:String = "fa",
        modifier: Modifier = Modifier){

        var foodType by remember { mutableStateOf("") }
        var suggestedPrice by remember { mutableStateOf("") }
        var pickupLocation by remember { mutableStateOf("") }
        var forgotCode by remember { mutableStateOf("") }
        var isMan by remember { mutableStateOf(false) }

        // برای اینکه هنگام کلیک روی تکست فیلد انتخاب غذا و انتخاب سلف شیت مربوط لانچ بشود
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        val focusStateFoodType = remember { mutableStateOf(false) }
        val focusStatePickupLocation = remember { mutableStateOf(false) }

        val foodTypeModifier = Modifier.onFocusChanged { state ->
            if (state.isFocused && !focusStateFoodType.value) {
                focusStateFoodType.value = true
                onSelectFoodType()
            } else if (!state.isFocused) {
                focusStateFoodType.value = false
            }
        }

        val pickupLocationModifier = Modifier.onFocusChanged { state ->
            if (state.isFocused && !focusStatePickupLocation.value) {
                focusStatePickupLocation.value = true
                onSelectPickupLocation()
            } else if (!state.isFocused) {
                focusStatePickupLocation.value = false
            }
        }


        val layoutDirection = if (lang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr

        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            BoxWithConstraints (
                modifier = Modifier
            ) {
                val screenWidth = maxWidth
                val screenHeight = maxHeight

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White),
                    verticalArrangement = Arrangement.spacedBy(screenHeight * 0.013f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        contentAlignment = Alignment.Center
                    ){
                        HorizontalDivider(
                            Modifier.width(24.dp),
                            thickness = 4.dp,
                            color = Gray04
                        )
                    }
                    OutlinedTextField(
                        value = viewModel.FoodType.value,
                        onValueChange = { },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.select_food_type),
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.vc_salad),
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = if (lang == "fa") Icons.Default.KeyboardArrowLeft else Icons.Default.KeyboardArrowRight,
                                contentDescription = null
                            )
                        },
                        readOnly = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = GrayC,
                            focusedBorderColor = NationsBlue,
                            errorBorderColor = Err,
                            focusedLeadingIconColor = NationsBlue,
                            unfocusedLeadingIconColor = GrayC,
                            errorLeadingIconColor = Err,
                            focusedTextColor = NationsBlue,
                            unfocusedTextColor = GrayC,
                            errorTextColor = Err,
                            focusedPlaceholderColor = NationsBlue,
                            unfocusedPlaceholderColor = GrayC,
                            errorPlaceholderColor = Err,
                            unfocusedTrailingIconColor = GrayC,
                            focusedTrailingIconColor = NationsBlue
                        ),
//            keyboardOptions = KeyboardOptions(
//                keyboardType =  KeyboardType.Number
//            ),
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth(0.88f)
                            .height(56.dp)
                            .focusRequester(focusRequester)
                            .then(foodTypeModifier)
                    )

                    OutlinedTextField(
                        value = suggestedPrice,
                        onValueChange = {
                            suggestedPrice = it
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.suggested_price),
                                style = MaterialTheme.typography.bodySmall)
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.vc_coins),
                                contentDescription = null
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = GrayC,
                            focusedBorderColor = NationsBlue,
                            errorBorderColor = Err,
                            focusedLeadingIconColor = NationsBlue,
                            unfocusedLeadingIconColor = GrayC,
                            errorLeadingIconColor = Err,
                            focusedTextColor = NationsBlue,
                            unfocusedTextColor = GrayC,
                            errorTextColor = Err,
                            focusedPlaceholderColor = NationsBlue,
                            unfocusedPlaceholderColor = GrayC,
                            errorPlaceholderColor = Err
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType =  KeyboardType.Number
                        ),
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth(0.88f)
                            .height(56.dp)
                    )

                    OutlinedTextField(
                        value = viewModel.PickupLocation.value,
                        onValueChange = {
                            onSelectPickupLocation()
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.pickup_location),
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.vc_building_store),
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = if (lang == "fa") Icons.Default.KeyboardArrowLeft else Icons.Default.KeyboardArrowRight,
                                contentDescription = null
                            )
                        },
                        readOnly = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = GrayC,
                            focusedBorderColor = NationsBlue,
                            errorBorderColor = Err,
                            focusedLeadingIconColor = NationsBlue,
                            unfocusedLeadingIconColor = GrayC,
                            errorLeadingIconColor = Err,
                            focusedTextColor = NationsBlue,
                            unfocusedTextColor = GrayC,
                            errorTextColor = Err,
                            focusedPlaceholderColor = NationsBlue,
                            unfocusedPlaceholderColor = GrayC,
                            errorPlaceholderColor = Err,
                            focusedTrailingIconColor = NationsBlue,
                            unfocusedTrailingIconColor = GrayC
                        ),
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth(0.88f)
                            .height(56.dp)
                            .focusRequester(focusRequester)
                            .then(pickupLocationModifier)
                    )

                    OutlinedTextField(
                        value = forgotCode,
                        onValueChange = {
                            forgotCode = it
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.forgot_code),
                                style = MaterialTheme.typography.bodySmall)
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.vc_key),
                                contentDescription = null
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = GrayC,
                            focusedBorderColor = NationsBlue,
                            errorBorderColor = Err,
                            focusedLeadingIconColor = NationsBlue,
                            unfocusedLeadingIconColor = GrayC,
                            errorLeadingIconColor = Err,
                            focusedTextColor = NationsBlue,
                            unfocusedTextColor = GrayC,
                            errorTextColor = Err,
                            focusedPlaceholderColor = NationsBlue,
                            unfocusedPlaceholderColor = GrayC,
                            errorPlaceholderColor = Err
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType =  KeyboardType.Number
                        ),
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth(0.88f)
                            .height(56.dp)
                    )

                    Row(modifier = Modifier
                        .fillMaxWidth(0.88f),
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        RadioButton(selected = isMan,
                            onClick = {
                                isMan = true
                            },
                            colors =  RadioButtonDefaults. colors(
                                unselectedColor = GrayC,
                                selectedColor = NationsBlue
                            )
                        )
                        Spacer(modifier = Modifier.width(screenWidth * 0.01f))
                        Text(text = stringResource(id = R.string.gentlemen),
                            style = MaterialTheme.typography.bodySmall,
                            color = Gunmetal,
                        )
                        Spacer(modifier = Modifier.width(screenWidth * 0.04f))
                        RadioButton(selected = !isMan,
                            onClick = {
                                isMan = false
                            },
                            colors =  RadioButtonDefaults.colors(
                                unselectedColor = GrayC,
                                selectedColor = NationsBlue
                            )
                        )
                        Spacer(modifier = Modifier.width(screenWidth * 0.01f))
                        Text(text = stringResource(id = R.string.ladies),
                            style = MaterialTheme.typography.bodySmall,
                            color = Gunmetal,
                        )
                    }

                    Spacer(modifier = Modifier.size(screenHeight * 0.008f))

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
                        Icon(imageVector = if (lang == "fa") Icons.Default.ArrowForward else Icons.Default.ArrowBack , contentDescription = null )
                        Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                        Text(stringResource(id = R.string.add_food),
                            fontFamily = FontFamily(Font(R.font.ir_medium)),
                            fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.width(screenWidth * 0.01f))

                }
            }
        }


    }

    @Composable
    fun SheetValueSelectFoodType(
        viewModel: Foodviewmodel = viewModel(),
        isExpanded: Boolean,
        currentLang: String,
        onClose: () -> Unit,
        modifier: Modifier = Modifier){

        val context = LocalContext.current
        val itemsFoodsType = if (currentLang == "fa") listOf(
            MenuItem("قرمه سبزی", painterResource(id = R.drawable.ic_ghorme)),
            MenuItem("قرمه سبزی", painterResource(id = R.drawable.ic_ghorme)),
            MenuItem("قرمه سبزی", painterResource(id = R.drawable.ic_ghorme)),
            MenuItem("قرمه سبزی", painterResource(id = R.drawable.ic_ghorme)),
            MenuItem("قرمه سبزی", painterResource(id = R.drawable.ic_ghorme)),
            MenuItem("قرمه سبزی", painterResource(id = R.drawable.ic_ghorme)),
            MenuItem("قرمه سبزی", painterResource(id = R.drawable.ic_ghorme)),
        ) else listOf(
            MenuItem("Ghormeh Sabzi", painterResource(id = R.drawable.ic_ghorme)),
            MenuItem("Ghormeh Sabzi", painterResource(id = R.drawable.ic_ghorme)),
            MenuItem("Ghormeh Sabzi", painterResource(id = R.drawable.ic_ghorme)),
            MenuItem("Ghormeh Sabzi", painterResource(id = R.drawable.ic_ghorme)),
            MenuItem("Ghormeh Sabzi", painterResource(id = R.drawable.ic_ghorme)),
            MenuItem("Ghormeh Sabzi", painterResource(id = R.drawable.ic_ghorme)),
            MenuItem("Ghormeh Sabzi", painterResource(id = R.drawable.ic_ghorme)),
            )

        val layoutDirection = if (currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr

        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            BoxWithConstraints (
                modifier = Modifier
            ) {
                val screenWidth = maxWidth
                val screenHeight = maxHeight

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.88f)
                            .height(32.dp),
//            contentAlignment = Alignment.Center
                    ){
                        if (!isExpanded){
                            HorizontalDivider(
                                Modifier
                                    .width(24.dp)
                                    .align(Alignment.Center),
                                thickness = 4.dp,
                                color = Gray04
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.vc_close),
                                contentDescription = null,
                                tint = Gunmetal,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(48.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            onClose()
                                        }
                                    }
                            )
                        }

                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = screenHeight * 0.45f, max = screenHeight * 0.8f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(screenHeight * 0.013f)
                    ) { items(itemsFoodsType.size) { item ->

                        Box(modifier = Modifier
                            .width(screenWidth * 0.88f)
                            .height(60.dp)
                            .background(Water, RoundedCornerShape(16.dp))
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    viewModel.FoodType.value = itemsFoodsType[item].text
                                    onClose()
                                }
                            }
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(0.99f)
                                    .fillMaxHeight()
                                    .align(Alignment.CenterStart)
                                    .background(White, RoundedCornerShape(16.dp))
                                    .clip(RoundedCornerShape(16.dp)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                                Image(
                                    painter = itemsFoodsType[item].icon,
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                )

                                Text(text = itemsFoodsType[item].text,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Gunmetal,
                                )

                            }

                        }

                    }
                    }
                }
            }
        }

    }

    @Composable
    fun SheetValueSelectPickupLocation(
        viewModel: Foodviewmodel = viewModel(),
        isExpanded: Boolean,
        currentLang: String,
        onClose: () -> Unit,
        modifier: Modifier = Modifier){

        val context = LocalContext.current
        val itemsPickupLocation = if (currentLang == "fa") listOf(
            MenuItem("سلف مرکزی", painterResource(id = R.drawable.ic_kabab)),
            MenuItem("سلف هنر و معماری", painterResource(id = R.drawable.ic_kabab)),
            MenuItem("سلف علوم پایه", painterResource(id = R.drawable.ic_kabab)),
            MenuItem("سلف مکمل 1", painterResource(id = R.drawable.ic_kabab)),
            MenuItem("سلف مکمل 2", painterResource(id = R.drawable.ic_kabab)),
            MenuItem("سلف مکمل 3", painterResource(id = R.drawable.ic_kabab)),
        )else listOf(
                MenuItem("Central Faculty", painterResource(id = R.drawable.ic_kabab)),
                MenuItem("Faculty of Architecture", painterResource(id = R.drawable.ic_kabab)),
                MenuItem("Central Faculty", painterResource(id = R.drawable.ic_kabab)),
                MenuItem("Central Faculty", painterResource(id = R.drawable.ic_kabab)),
                MenuItem("Central Faculty", painterResource(id = R.drawable.ic_kabab))
        )

        val layoutDirection = if (currentLang == "fa") LayoutDirection.Rtl else LayoutDirection.Ltr

        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            BoxWithConstraints (
                modifier = Modifier
            ) {
                val screenWidth = maxWidth
                val screenHeight = maxHeight

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.88f)
                            .height(32.dp),
//            contentAlignment = Alignment.Center
                    ){
                        if (!isExpanded){
                            HorizontalDivider(
                                Modifier
                                    .width(24.dp)
                                    .align(Alignment.Center),
                                thickness = 4.dp,
                                color = Gray04
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.vc_close),
                                contentDescription = null,
                                tint = Gunmetal,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .size(48.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            onClose()
                                        }
                                    }
                            )
                        }

                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = screenHeight * 0.45f, max = screenHeight * 0.8f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) { items(itemsPickupLocation.size) { item ->

                        Box(modifier = Modifier
                            .fillMaxWidth(0.88f)
                            .height(60.dp)
                            .background(Water, RoundedCornerShape(16.dp))
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    viewModel.PickupLocation.value = itemsPickupLocation[item].text
                                    onClose()
//                        isVisibleItem[page] = !isVisibleItem[page]
                                }
                            }
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(0.99f)
                                    .fillMaxHeight()
                                    .align(Alignment.CenterStart)
                                    .background(White, RoundedCornerShape(16.dp))
                                    .clip(RoundedCornerShape(16.dp)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Spacer(modifier = Modifier.width(screenWidth * 0.05f))
                                Image(
                                    painter = itemsPickupLocation[item].icon,
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                )

                                Text(text = itemsPickupLocation[item].text,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Gunmetal,
                                )

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
    fun ReservationFoodPreview() {
        SinarTheme {
            ReservationFoodScreen()
        }
    }