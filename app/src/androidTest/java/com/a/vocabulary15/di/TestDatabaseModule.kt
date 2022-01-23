package com.a.vocabulary15.di

import android.app.Application
import androidx.room.Room
import com.a.vocabulary15.data.local.VocabularyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): VocabularyDatabase {
        return Room.inMemoryDatabaseBuilder(
            application,
            VocabularyDatabase::class.java
        )
            .build()
    }
}