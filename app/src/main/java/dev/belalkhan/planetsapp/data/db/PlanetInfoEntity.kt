package dev.belalkhan.planetsapp.data.db

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import dev.belalkhan.planetsapp.data.remote.models.PlanetInfoDto
import kotlinx.serialization.json.Json

@Entity(tableName = "planets_info")
data class PlanetInfoEntity(
    @PrimaryKey(autoGenerate = false)
    val uid: String,
    val id: String,
    val description: String,
    val _properties: String,
    val v: Int,
) {
    @get:Ignore
    val properties: PlanetInfoDto.Properties
        get() = Json.decodeFromString(_properties)
}
