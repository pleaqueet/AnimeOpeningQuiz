package com.example.animeopening.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.animeopening.domain.models.Opening
import com.example.animeopening.domain.models.Pack

@Dao
interface OpeningDao {
    @Query("SELECT * FROM opening ORDER BY id ASC")
    fun getOpenings(): LiveData<List<Opening>>

    @Query("SELECT * FROM pack ORDER BY id ASC")
    fun getPacks(): LiveData<List<Pack>>

    @Query("SELECT * FROM opening WHERE id = :id")
    suspend fun getOpeningById(id: Int): Opening?

    @Update
    fun updatePack(pack: Pack)
}
