package com.a.vocabulary15.di

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.usecases.*
import com.a.vocabulary15.presentation.element.ElementsViewModel
import com.a.vocabulary15.presentation.group.MainViewModel
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
        getElements: GetElements,
        setElement: SetElement,
        deleteElement: DeleteElement,
        editElement: EditElement,
        deleteGroupWithElements: DeleteGroupWithElements
    ): ElementsViewModel =
        ElementsViewModel(getElements, setElement, deleteElement, editElement, deleteGroupWithElements)

    @Provides
    fun provideGetElement(repository: Repository): GetElements = GetElementsImpl(repository)

    @Provides
    fun provideDeleteGroupWithElements(repository: Repository): DeleteGroupWithElements =
        DeleteGroupWithElementsImpl(repository)

    @Provides
    fun provideSetElement(repository: Repository): SetElement = SetElementImpl(repository)

    @Provides
    fun provideDeleteElement(repository: Repository): DeleteElement = DeleteElementImpl(repository)

    @Provides
    fun provideEditElement(repository: Repository): EditElement = EditElementImpl(repository)
}