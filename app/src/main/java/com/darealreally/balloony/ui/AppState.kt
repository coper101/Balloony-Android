package com.darealreally.balloony.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.darealreally.balloony.data.Balloon
import com.darealreally.balloony.ui.theme.ColorCombinations

class AppState(
    private val content: MutableState<ScreenContent>
) {
    // Data
    private val _balloons = listOf(
        Balloon(
            name = "Cosmic",
            gradientColors = listOf(
                ColorCombinations.combination5.first,
                ColorCombinations.combination5.second,
            ),
            topViewLength = 94F
        ),
        Balloon(
            name = "Saturn",
            gradientColors = listOf(
                ColorCombinations.combination4.first,
                ColorCombinations.combination4.second,
            ),
            topViewLength = 201F
        ),
        Balloon(
            name = "Aqua",
            gradientColors = listOf(
                ColorCombinations.combination2.first,
                ColorCombinations.combination2.second,
            ),
            topViewLength = 115F
        ),
        Balloon(
            name = "Sun",
            gradientColors = listOf(
                ColorCombinations.combination3.first,
                ColorCombinations.combination3.second,
            ),
            topViewLength = 48F
        ),
        Balloon(
            name = "Unicorn",
            gradientColors = listOf(
                ColorCombinations.combination1.first,
                ColorCombinations.combination1.second,
            ),
            topViewLength = 86F
        )
    )

    val balloons: List<Balloon>
        get() = _balloons

    // Setters & Getters
    val screenContent: ScreenContent
        get() = getContent()

    val setScreenContent: (ScreenContent) -> Unit = {
        content.value = it
    }

    fun getContent() = content.value
}

class StatelessApp {
    // Data
    val balloons = listOf(
        Balloon(
            name = "Cosmic",
            gradientColors = listOf(
                ColorCombinations.combination5.first,
                ColorCombinations.combination5.second,
            ),
            topViewLength = 94F
        ),
        Balloon(
            name = "Saturn",
            gradientColors = listOf(
                ColorCombinations.combination4.first,
                ColorCombinations.combination4.second,
            ),
            topViewLength = 201F
        ),
        Balloon(
            name = "Aqua",
            gradientColors = listOf(
                ColorCombinations.combination2.first,
                ColorCombinations.combination2.second,
            ),
            topViewLength = 115F
        ),
        Balloon(
            name = "Sun",
            gradientColors = listOf(
                ColorCombinations.combination3.first,
                ColorCombinations.combination3.second,
            ),
            topViewLength = 48F
        ),
        Balloon(
            name = "Unicorn",
            gradientColors = listOf(
                ColorCombinations.combination1.first,
                ColorCombinations.combination1.second,
            ),
            topViewLength = 86F
        )
    )
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