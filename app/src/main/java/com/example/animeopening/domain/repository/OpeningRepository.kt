package com.example.animeopening.domain.repository

import androidx.lifecycle.LiveData
import com.example.animeopening.domain.models.Opening
import kotlinx.coroutines.flow.Flow

interface OpeningRepository {
    fun getOpenings(): LiveData<List<Opening>>
}