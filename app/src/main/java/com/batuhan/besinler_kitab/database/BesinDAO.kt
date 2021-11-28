package com.batuhan.besinler_kitab.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.batuhan.besinler_kitab.model.BesinList

@Dao
interface BesinDAO {
    @Insert
    suspend fun insertAll(vararg besin:BesinList):List<Long>
    @Query("SELECT * FROM besinlist")
    suspend fun getAll():List<BesinList>
    @Query("SELECT * FROM besinlist WHERE uuid=:besinId")
    suspend fun getID(besinId:Int):BesinList
    @Query("DELETE FROM besinlist")
    suspend fun deleteAll()
}