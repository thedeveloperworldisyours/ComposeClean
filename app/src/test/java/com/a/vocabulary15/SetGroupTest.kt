package com.a.vocabulary15

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.usecases.SetGroup
import com.a.vocabulary15.domain.usecases.SetGroupImpl
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetGroupTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var setGroup: SetGroup

    private var group = Group(1, "name")

    @Before
    fun setup(){
        setGroup = SetGroupImpl(repository)
    }

    @Test
    fun `set group with success`() {
        runBlocking {
            setGroup.invoke(group)

            verify(repository).setGroup(group)
        }
    }
}