package com.a.vocabulary15.viewmodel

import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.usecases.GetGroup
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
    private lateinit var getGroup: GetGroup

    @Mock
    private lateinit var setGroup: SetGroup

    private lateinit var groupViewModel: MainViewModel

    private val group = Group(1, "name")

    @Before
    fun setup() {
        groupViewModel = MainViewModel(getGroup, setGroup)
    }

    @Test
    fun `get group successfully`(){
        runBlocking {

            verify(getGroup).invoke()
        }
    }

    @Test
    fun `set group successfully`() {
        runBlocking {
            groupViewModel.insertAndGetGroup(group)

            verify(setGroup).invoke(group)
        }
    }
}