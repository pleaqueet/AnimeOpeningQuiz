package com.example.animeopening.data.repository

import androidx.lifecycle.LiveData
import com.example.animeopening.data.room.OpeningDao
import com.example.animeopening.domain.models.Opening
import com.example.animeopening.domain.repository.OpeningRepository
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors

class OpeningRepositoryImpl(private val dao: OpeningDao) : OpeningRepository {

    override fun getOpenings(): LiveData<List<Opening>> {
        return dao.getOpenings()
    }

    private val executor = Executors.newSingleThreadExecutor()

    override fun updateOpening(opening: Opening){
        executor.execute {
            dao.updateOpening(opening)
        }
    }
}