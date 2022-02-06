package com.example.animeopening.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.animeopening.domain.models.Opening
import kotlinx.coroutines.flow.Flow

@Dao
interface OpeningDao {
    @Query("SELECT * FROM opening ORDER BY id ASC")
    fun getOpenings(): LiveData<List<Opening>>

    @Query("SELECT * FROM opening WHERE id = :id")
    suspend fun getOpeningById(id: Int): Opening?
}
