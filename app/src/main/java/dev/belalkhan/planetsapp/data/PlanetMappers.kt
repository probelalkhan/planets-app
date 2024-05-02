package dev.belalkhan.planetsapp.data

import dev.belalkhan.planetsapp.data.db.PlanetEntity
import dev.belalkhan.planetsapp.data.db.PlanetInfoEntity
import dev.belalkhan.planetsapp.data.remote.models.PlanetDto
import dev.belalkhan.planetsapp.data.remote.models.PlanetInfoDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun PlanetDto.toPlanetEntity() = PlanetEntity(
    uid = this.uid,
    name = this.name,
    url = this.url,
)

fun PlanetInfoDto.toPlanetInfoEntity() = PlanetInfoEntity(
    id = this.id,
    description = this.description,
    _properties = Json.encodeToString(this.properties),
    uid = this.uid,
    v = this.v,
)
