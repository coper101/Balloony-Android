package com.darealreally.balloony.data

import com.darealreally.balloony.R

data class Feature(
    val iconId: Int,
    val title: String
)

val features = listOf(
    Feature(R.drawable.ic_spaceship, "Helium-Inflated"),
    Feature(R.drawable.ic_string, "Strings included"),
    Feature(R.drawable.ic_thunderbolt, "Long-lasting")
)