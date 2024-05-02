package dev.belalkhan.planetsapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planets")
data class PlanetEntity(
    @PrimaryKey(autoGenerate = false)
    val uid: String,
    val name: String,
    val url: String,
)
