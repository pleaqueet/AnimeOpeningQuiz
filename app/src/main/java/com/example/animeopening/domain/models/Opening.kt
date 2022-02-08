package com.example.animeopening.domain.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "opening")
@Parcelize
data class Opening(
    @PrimaryKey val id: Int,
    val difficulty: Int,
    val opening: String,
    val mp3: String
) : Parcelable