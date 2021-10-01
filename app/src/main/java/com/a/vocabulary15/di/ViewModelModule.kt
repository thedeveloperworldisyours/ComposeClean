package com.a.vocabulary15.di

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.usecases.*
import com.a.vocabulary15.presentation.ElementsViewModel
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
        getGroup: GetGroup,
        setGroup: SetGroup
    ): MainViewModel = MainViewModel(getGroup, setGroup)

    @Provides
    fun provideGetGroup(repository: Repository): GetGroup = GetGroupImpl(repository)

    @Provides
    fun provideSetGroup(repository: Repository): SetGroup = SetGroupImpl(repository)

    @Provides
    fun provideElementViewModel(
        getElement: GetElement,
        setElement: SetElement,
        deleteGroupWithElements: DeleteGroupWithElements
    ): ElementsViewModel = ElementsViewModel(getElement, setElement, deleteGroupWithElements)
    @Provides
    fun provideGetElement(repository: Repository): GetElement = GetElementImpl(repository)

    @Provides
    fun provideDeleteGroupWithElements(repository: Repository): DeleteGroupWithElements =
        DeleteGroupWithElementsImpl(repository)

    @Provides
    fun provideSetElement(repository: Repository): SetElement = SetElementImpl(repository)
}