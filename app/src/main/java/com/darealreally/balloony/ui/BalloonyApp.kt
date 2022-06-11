package com.darealreally.balloony.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.darealreally.balloony.Graph
import com.darealreally.balloony.ui.main.MainScreen
import com.darealreally.balloony.ui.main.MainUiState
import com.darealreally.balloony.ui.splash.SplashScreen
import com.darealreally.balloony.ui.splash.SplashUiState

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
            setContent = { appState.setScreenContent(it) },
            splashUiState = SplashUiState(Graph.balloons)
        )

        // Layer 2: MAIN
        AnimatedVisibility(
            visible = appState.screenContent == ScreenContent.Main,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            MainScreen(
                mainUiState = MainUiState(Graph.balloons),
            )
        }

    } //: Box
}