package com.example.animeopening.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "opening")
data class Opening(
    @PrimaryKey val id: Int,
    val difficulty: Int,
    val opening: String,
    val mp3: String,
    val url: String
)
