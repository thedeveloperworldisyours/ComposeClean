package com.a.vocabulary15.viewmodel

import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.usecases.GetGroups
import com.a.vocabulary15.domain.usecases.SetGroup
import com.a.vocabulary15.presentation.group.MainViewModel
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GroupViewModelTest {
    @Mock
    private lateinit var getGroups: GetGroups

    @Mock
    private lateinit var setGroup: SetGroup

    private lateinit var groupViewModel: MainViewModel

    private val group = Group(1, "name")

    @Before
    fun setup() {
        groupViewModel = MainViewModel(getGroups, setGroup)
    }

    @Test
    fun `get group successfully`(){
        runBlocking {

            verify(getGroups).invoke()
        }
    }

    @Test
    fun `set group successfully`() {
        runBlocking {
            groupViewModel.insertGroup(group)

            verify(setGroup).invoke(group)
        }
    }
}