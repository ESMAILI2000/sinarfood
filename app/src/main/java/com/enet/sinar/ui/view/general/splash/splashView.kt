package com.enet.sinar.ui.view.general.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enet.sinar.R
import com.enet.sinar.ui.theme.GrayF
import com.enet.sinar.ui.theme.Gunmetal
import com.enet.sinar.ui.theme.SinarTheme
import com.enet.sinar.ui.utility.MySharedPreferences.getUserType
import com.enet.sinar.ui.utility.MySharedPreferences.getisLogin
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val isLogin = getisLogin(context)
//    val userType = getUserType()
    LaunchedEffect(Unit) {
        delay(3000) // مکث به مدت 3000 میلی‌ثانیه (3 ثانیه)
//        if (isLogin){
//            onNavigateToHome()
//        }else{
            onNavigateToLogin()
//        }
    }

    BoxWithConstraints (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(top = screenHeight * 0.124f)
                    .size(screenHeight * 0.192f)
                    .align(Alignment.CenterHorizontally)
            ){
                Image(
                    painter = painterResource(id = R.drawable.logo), contentDescription = null,
                    Modifier.fillMaxSize()

                )
            }
            Text(
                text = stringResource(id = R.string.app_name_splash),
                Modifier
                    .padding(top = screenHeight * 0.022f)
                    .align(Alignment.CenterHorizontally),
                color = Gunmetal,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                fontFamily = FontFamily(Font(R.font.ir_heavy)),
                fontWeight = FontWeight.W900
            )
            Spacer(modifier = Modifier.height(screenHeight * 0.011f))
            Text(
                text = stringResource(id = R.string.splash_tagline),
                Modifier
                    .width(screenWidth * 0.73f)
                    .align(Alignment.CenterHorizontally),
                color = GrayF,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            )

            Spacer(modifier = Modifier.height(screenHeight * 0.022f))

            CustomLoadingIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterHorizontally),
                numSegments = 8, // 8 پره
                segmentWidth = 6f, // عرض هر پره
                segmentLength = 20f, // طول هر پره
                innerRadiusFraction = 0.7f // فضای داخلی دایره مرکزی
            )

//            Spacer(modifier = Modifier.height(screenHeight * 0.022f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                .height(screenHeight * 0.495f)
//                    .align(Alignment.BottomCenter)
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_splash), contentDescription = null,
                    Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop

                )
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
fun SplashPreview() {
    SinarTheme {
        SplashScreen()
    }
}