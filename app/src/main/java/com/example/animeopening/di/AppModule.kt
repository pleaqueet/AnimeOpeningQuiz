package com.example.animeopening.di

import android.app.Application
import androidx.room.Room
import com.example.animeopening.data.repository.OpeningRepositoryImpl
import com.example.animeopening.data.room.OpeningDatabase
import com.example.animeopening.domain.repository.OpeningRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOpeningDatabase(app: Application): OpeningDatabase {
        return Room.databaseBuilder(
            app,
            OpeningDatabase::class.java,
            OpeningDatabase.DATABASE_NAME
        ).createFromAsset("opening.db").build()
    }

    @Provides
    @Singleton
    fun provideOpeningRepository(db: OpeningDatabase): OpeningRepository {
        return OpeningRepositoryImpl(db.openingDao)
    }
}