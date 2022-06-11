package com.darealreally.balloony.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Size
import com.darealreally.balloony.R

data class Balloon(
    var name: String,
    var price: Double = 8.50,
    var gradientColors: List<Color>,
    var topViewLength: Float,
    var frontViewSizeSmall: Size = Size(109F, 132F),
    var frontViewSizeBig: Size = Size(146F, 177F),
    var features: List<Feature> = listOf(
        Feature(R.drawable.ic_spaceship, "Helium-Inflated"),
        Feature(R.drawable.ic_string, "Strings included"),
        Feature(R.drawable.ic_thunderbolt, "Long-lasting")
    )
)