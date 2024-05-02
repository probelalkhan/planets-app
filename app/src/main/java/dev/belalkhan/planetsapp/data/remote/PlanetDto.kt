package dev.belalkhan.planetsapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlanetDto(
    @SerialName("uid")
    val uid: String,

    @SerialName("name")
    val name: String,

    @SerialName("url")
    val url: String,
)
