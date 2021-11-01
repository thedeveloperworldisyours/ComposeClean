package com.a.vocabulary15

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.usecases.GetGroup
import com.a.vocabulary15.domain.usecases.GetGroupImpl
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetGroupTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var getGroup: GetGroup


    @Before
    fun setup(){
        getGroup = GetGroupImpl(repository)
    }

    @Test
    fun `get group successful`(){
        runBlocking {
            getGroup.invoke()

            verify(repository).getGroup()
        }
    }
}