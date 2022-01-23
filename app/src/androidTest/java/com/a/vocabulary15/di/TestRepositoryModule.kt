package com.a.vocabulary15.di

import com.a.vocabulary15.data.local.VocabularyDatabase
import com.a.vocabulary15.data.repository.RepositoryImpl
import com.a.vocabulary15.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestRepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        database: VocabularyDatabase
    ): Repository = RepositoryImpl(database)
}