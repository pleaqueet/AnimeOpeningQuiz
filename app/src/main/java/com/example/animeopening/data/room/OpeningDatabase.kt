package com.example.animeopening.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.animeopening.domain.models.Opening
import com.example.animeopening.domain.models.Pack

@Database(
    entities = [Opening::class, Pack::class],
    version = 1,
    exportSchema = false
)
abstract class OpeningDatabase : RoomDatabase() {
    abstract val openingDao: OpeningDao

    companion object {
        const val DATABASE_NAME = "opening"
    }
}