package dev.belalkhan.planetsapp.data

import dev.belalkhan.planetsapp.data.db.PlanetEntity
import dev.belalkhan.planetsapp.data.remote.PlanetDto

fun PlanetDto.toPlanetEntity() = PlanetEntity(
    uid = this.uid,
    name = this.name,
    url = this.url
)

fun PlanetEntity.toPlanetDto() = PlanetDto(
    uid = this.uid,
    name = this.name,
    url = this.url
)