package com.example.animeopening.data.repository

import androidx.lifecycle.LiveData
import com.example.animeopening.data.room.OpeningDao
import com.example.animeopening.domain.models.Opening
import com.example.animeopening.domain.models.Pack
import com.example.animeopening.domain.repository.OpeningRepository
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors

class OpeningRepositoryImpl(private val dao: OpeningDao) : OpeningRepository {

    override fun getOpenings(): LiveData<List<Opening>> {
        return dao.getOpenings()
    }

    override fun getPacks(): LiveData<List<Pack>> {
        return dao.getPacks()
    }

    override suspend fun updatePack(pack: Pack) {
        dao.updatePack(pack)
    }

    private val executor = Executors.newSingleThreadExecutor()

}