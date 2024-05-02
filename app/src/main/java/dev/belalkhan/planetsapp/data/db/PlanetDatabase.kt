package dev.belalkhan.planetsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlanetEntity::class, PlanetInfoEntity::class], version = 1)
abstract class PlanetDatabase : RoomDatabase() {

    abstract val planetDao: PlanetDao
    abstract val planetInfoDao: PlanetInfoDao
}
