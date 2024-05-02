package dev.belalkhan.planetsapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlanetInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(planetInfo: PlanetInfoEntity)

    @Query("SELECT * FROM planets_info WHERE uid = :id")
    suspend fun getPlanetInfo(id: String): PlanetInfoEntity?
}
