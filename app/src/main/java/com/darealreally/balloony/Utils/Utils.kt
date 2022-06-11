package com.darealreally.balloony.Utils

fun Double.round(
    places: Int
) = String.format("%.${places}f", this)
