package dev.belalkhan.planetsapp.planets

import androidx.annotation.DrawableRes
import dev.belalkhan.planetsapp.R

private val planetIcons = listOf(
    R.drawable.planet1,
    R.drawable.planet2,
    R.drawable.planet3,
    R.drawable.planet4,
    R.drawable.planet5,
    R.drawable.planet6,
)

@get:DrawableRes
val planetIcon: Int
    get() = planetIcons.random()
