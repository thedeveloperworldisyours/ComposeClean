package com.a.vocabulary15.viewmodel

import app.cash.turbine.test
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.Statistics
import com.a.vocabulary15.domain.usecases.GetGroups
import com.a.vocabulary15.domain.usecases.SetGroup
import com.a.vocabulary15.presentation.common.ViewState
import com.a.vocabulary15.presentation.group.MainViewModel
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Mock
    private lateinit var setGroup: SetGroup

    @Mock
    private lateinit var getGroups: GetGroups

    private lateinit var mainViewModel: MainViewModel

    val group = Group(1, "name")
    private val secondGroup = Group(2, "Second Name")
    private val groups = mutableListOf<Group>()


    @Before
    fun setup () {
        mainViewModel = MainViewModel(getGroups, setGroup)
        groups.add(group)
        groups.add(secondGroup)
    }

    /*@Test
    fun `get group successfully`() = runBlockingTest {

        Mockito.`when`(getGroups.invoke()).thenReturn(flow { emit(groups) })

        mainViewModel.groups.test {
            val emission = awaitItem()
            Truth.assertThat(emission).isEqualTo(groups)
            cancelAndConsumeRemainingEvents()
        }
    }*/

    @Test
    fun `set group successfully`() = runBlockingTest {

        mainViewModel.insertGroup(group)

        verify(setGroup).invoke(group)
    }
}