package com.a.vocabulary15.di

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.usecases.SetGroup
import com.a.vocabulary15.domain.usecases.SetGroupImpl
import com.a.vocabulary15.presentation.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class ViewModelModule {

    @Provides
    fun provideMainViewModel(
        setGroup: SetGroup
    ): MainViewModel = MainViewModel(setGroup)

    @Provides
    fun provideSetGroup(repository: Repository) : SetGroup = SetGroupImpl(repository)

}