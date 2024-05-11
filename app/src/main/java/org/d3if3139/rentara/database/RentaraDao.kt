package org.d3if3139.rentara.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3139.rentara.model.Rentara

@Dao
interface RentaraDao {

    @Insert
    suspend fun insert(rentara: Rentara)
    @Update
    suspend fun update(rentara: Rentara)

    @Query("SELECT * FROM rentara ORDER BY judul ASC")
    fun getData(): Flow<List<Rentara>>

    @Query("SELECT * FROM rentara WHERE id = :id")
    suspend fun getDataById(id: Long): Rentara?

    @Query("SELECT * FROM rentara WHERE judul = :title")
    fun searchDataByTitle(title: String): Rentara?

    @Query("DELETE FROM Rentara WHERE id = :id")
    suspend fun deleteById(id: Long)
}