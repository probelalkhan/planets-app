package dev.belalkhan.planetsapp.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlanetInfoApiResponse(
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: PlanetInfoDto,
)
