package com.darealreally.balloony

import com.darealreally.balloony.data.Balloon
import com.darealreally.balloony.data.BalloonRepository
import com.darealreally.balloony.ui.theme.ColorCombinations

/**
 * Global Singleton dependency graph
 */
object Graph {
    private val balloonRepository = BalloonRepository()

    val balloons: List<Balloon>
        get() = balloonRepository.balloons

}

object MockGraph {

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