package com.darealreally.balloony.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class AppState(
    private val content: MutableState<ScreenContent>
) {
    // Setters & Getters
    val screenContent: ScreenContent
        get() = getContent()

    val setScreenContent: (ScreenContent) -> Unit = {
        content.value = it
    }

    private fun getContent() = content.value
}

@Composable
fun rememberAppState(
    content: MutableState<ScreenContent> = remember { mutableStateOf(ScreenContent.Splash) }
) = remember(
    content
) {
    AppState(
        content = content
    )
}