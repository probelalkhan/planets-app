package dev.belalkhan.planetsapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlanetsApiResponse(
    @SerialName("message")
    val message: String,
    @SerialName("next")
    val next: String?,
    @SerialName("previous")
    val previous: String?,
    @SerialName("results")
    val results: List<PlanetDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_records")
    val totalRecords: Int
)