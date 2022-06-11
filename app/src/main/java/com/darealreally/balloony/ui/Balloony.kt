package com.darealreally.balloony.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.darealreally.balloony.ui.main.MainScreen
import com.darealreally.balloony.ui.splash.SplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

enum class ScreenContent {
    Splash,
    Main
}

@Composable
fun BalloonyApp(
    appState: AppState = rememberAppState()
) {
    // UI
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        // Layer 1: SPLASH
        SplashScreen(
            setContent = { appState.setScreenContent(it) }
        )

        // Layer 2: MAIN
        AnimatedVisibility(
            visible = appState.screenContent == ScreenContent.Main,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            MainScreen()
        }

    } //: Box
}