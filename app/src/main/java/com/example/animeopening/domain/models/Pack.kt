package com.example.animeopening.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "pack")
@Parcelize
data class Pack(
    @PrimaryKey val id: Int,
    var isDownloading: Boolean = false,
    var isPlayed: Boolean
) : Parcelable