package com.darealreally.balloony.data

import com.darealreally.balloony.ui.theme.ColorCombinations

class BalloonRepository {

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