package com.enet.sinar.ui.view

import AppSection
import CitizenRoutes
import GeneralRoutes
import StaffRoutes
import StudentRoutes
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.enet.sinar.ui.theme.SinarTheme
import com.enet.sinar.ui.utility.MySharedPreferences
import com.enet.sinar.ui.utility.MySharedPreferences.getUserType
import com.enet.sinar.ui.utility.MySharedPreferences.getLang
import com.enet.sinar.ui.utility.MySharedPreferences.getisLogin
import com.enet.sinar.ui.utility.MySharedPreferences.setFoodType
import com.enet.sinar.ui.utility.MySharedPreferences.setPickupLocation
import com.enet.sinar.ui.utility.MySharedPreferences.setisLogin
import com.enet.sinar.ui.view.general.changePassword.ChangePasswordScreen
import com.enet.sinar.ui.view.general.exchange.ExchangeScreen
import com.enet.sinar.ui.view.general.login.LoginScreen
import com.enet.sinar.ui.view.general.otp.OtpScreen
import com.enet.sinar.ui.view.general.pay.InformationScreen
import com.enet.sinar.ui.view.general.splash.SplashScreen
import com.enet.sinar.ui.view.general.transaction.HistoryTransactionScreen
import com.enet.sinar.ui.view.general.wallet.WalletWordsScreen
import com.enet.sinar.ui.view.staff.StaffHomeScreen
import com.enet.sinar.ui.view.student.food.ReservationFoodScreen
import com.enet.sinar.ui.view.student.home.StudentHomeScreen
import kotlinx.coroutines.delay
import java.util.Locale


@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val lang = getLang(this) ?: "fa"
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.locale = locale
        config.setLayoutDirection(locale)

        resources.updateConfiguration(config, resources.displayMetrics)

        super.onCreate(savedInstanceState)
          setContent{
              var isDarkThemeEnabled by remember { mutableStateOf(true) }
              SinarTheme(darkTheme = isDarkThemeEnabled, dynamicColor = false) {
                    MyApp(modifier = Modifier.fillMaxSize(),
                        onToggleDarkTheme = {
                            isDarkThemeEnabled = !isDarkThemeEnabled
                        })
            }
        }
        hideNavigationBarCompat()
        setupKeyboardListener()
    }


    private fun setupKeyboardListener() {
        val rootView = window.decorView.findViewById<View>(android.R.id.content)
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight * 0.15) {
                // کیبورد باز شده
                hideNavigationBarCompat() // دوباره مخفی کن
            } else {
                // کیبورد بسته شده
                hideNavigationBarCompat() // دوباره مخفی کن
            }
        }
    }

    private fun hideNavigationBarCompat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            controller?.hide(WindowInsets.Type.navigationBars())
            controller?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = Color.GRAY // یا هر رنگ دلخواه مثل Color.White
            }

            // شفاف کردن نوار وضعیت
            window.statusBarColor = Color.TRANSPARENT


        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // رنگ آیکون‌ها مشکی
                    )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = Color.TRANSPARENT
            }
        }
    }

    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun MyApp(modifier: Modifier = Modifier,onToggleDarkTheme: () -> Unit) {

        val context = LocalContext.current
        val isLogin = getisLogin(context)
        val userType = getUserType(this) // نوع کاربر -> دانشجو ، کارمند ، شهروند
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "Splash") {

            // مسیر های عمومی
            composable("Splash") {
                SplashScreen(
                    onNavigateToLogin = {  navController.navigate("Login") },
                    onNavigateToHome = {
                        when(userType){
                            "Student" -> { navController.navigate("HomeStudent") }
                            "Staff" -> { navController.navigate("HomeStaff") }
                            "Citizen" -> { navController.navigate("HomeCitizen") }
                        }
                    }
                )
            }
            composable("Login") {
                LoginScreen(
                    onLoginStudent = {
                        navController.navigate("Otp")
                    },
                    onLoginHome = {
                        navController.navigate("HomeStudent")
                    },
                    onAuthentication = {
                        navController.navigate("Otp")
                    },
                    onRecoveryPassword = {
                        navController.navigate("ChangePassword")
                    }
                )
            }
            composable("AboutUs") {
            }
            composable("ChangePassword") {
                ChangePasswordScreen(
                    onNavigateToHome = {
                        when(userType){
                            "Student" -> { navController.navigate("HomeStudent") }
                            "Staff" -> { navController.navigate("HomeStaff") }
                            "Citizen" -> { navController.navigate("HomeCitizen") }
                        }
                    },
                    onNavigateToBack = {
                        navController.navigate("Login")
                    }
                )
            }
            composable("Exchange") {
                ExchangeScreen(
                    onNavigateToBack = {
                        navController.navigate("HomeStudent")
                    }
                )
            }
            composable("Otp") {
                OtpScreen(
                    onNavigateToHome = {
                        navController.navigate("WalletWords")
                    },
                    onNavigateToBack = {
                        navController.navigate("Login")
                    }
                )
            }
            composable("InformationPay") {
                InformationScreen(
                    onNavigateToBack = {
                        navController.navigate("HomeStudent")
                    }
                )
            }
            composable("HistoryTransaction") {
                HistoryTransactionScreen(
                    onNavigateToBack = {
                        navController.navigate("HomeStudent")
                    }
                )
            }
            composable("WalletWords") {
                WalletWordsScreen(
                    onNavigateToHome = {
                        navController.navigate("HomeStudent")
                    },
                    onNavigateToBack = {
                        navController.navigate("Otp")
                    }
                )
            }
            // مسیرهای دانشجو (Student Routes)
            composable("HomeStudent") {
                setPickupLocation(context,"")
                setFoodType(context,"")
                StudentHomeScreen(
                    onNavigateToInvoice = {
                        navController.navigate("HistoryTransaction")
                    },
                    onNavigateToExchange = {
                        navController.navigate("Exchange")
                    },
                    onNavigateToLogin = {
                        navController.navigate("Login")
                    },
                    onNavigateToPayment = {
                        navController.navigate("InformationPay")
                    },
                    onNavigateToTransaction = {

                    },
                    onNavigateToFoodReservation = {
                        navController.navigate("ReservationFood")
                    }
                )
            }
            composable("ReservationFood") {
                ReservationFoodScreen(
                    onNavigateToBack = {
                        navController.navigate("HomeStudent")
                    }
                )
            }

            // مسیرهای کارمند (Staff Routes)
            composable("HomeStaff") {
                StaffHomeScreen(
                    onLogout = {
                        navController.navigate("Login") {
                            popUpTo("HomeStaff") {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            // مسیرهای شهروند (Citizen Routes)
            composable("HomeCitizen") {
                StaffHomeScreen(
                    onLogout = {
                        navController.navigate("Login") {
                            popUpTo("HomeCitizen") {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }

}


//@SuppressLint("WrongConstant")
//@Composable
//fun HideSystemBarsEffect() {
//    val context = LocalContext.current
//    val window = (context as Activity).window
//
//    // فعال‌سازی حالت immersive sticky
//    WindowCompat.setDecorFitsSystemWindows(window, false)
//    val controller = WindowInsetsControllerCompat(window, window.decorView)
//    controller.hide(WindowInsetsCompat.Type.systemBars())
//    controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//
//    // گوش‌به‌زنگ ظاهر شدن نوار ناوبری
//    window.decorView.setOnApplyWindowInsetsListener { view, insets ->
//        if (insets.isVisible(WindowInsetsCompat.Type.navigationBars())) {
//            Handler(Looper.getMainLooper()).postDelayed({
//                controller.hide(WindowInsetsCompat.Type.navigationBars())
//            }, 2000) // بعد از ۲ ثانیه مخفی کن
//        }
//        view.onApplyWindowInsets(insets)
//    }
//
////    SideEffect {
////        window.decorView.setOnApplyWindowInsetsListener { view, insets ->
////            val controller = WindowInsetsControllerCompat(window, view)
////
////            if (insets.isVisible(WindowInsetsCompat.Type.navigationBars())) {
////                Handler(Looper.getMainLooper()).postDelayed({
////                    controller.hide(WindowInsetsCompat.Type.navigationBars())
////                }, 2000) // بعد از ۲ ثانیه مخفی کن
////            }
////
////            view.onApplyWindowInsets(insets)
////        }
////
////
////
////    }
//}


