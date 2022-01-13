package com.a.vocabulary15

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.usecases.GetGroups
import com.a.vocabulary15.domain.usecases.GetGroupsImpl
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetGroupsTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var getGroups: GetGroups

    @Before
    fun setup(){
        getGroups = GetGroupsImpl(repository)
    }

    @Test
    fun `get groups successfully`(){
        runBlocking {
            getGroups.invoke()

            verify(repository).getGroups()
        }
    }
}