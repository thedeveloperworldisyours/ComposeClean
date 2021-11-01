package com.a.vocabulary15

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.usecases.DeleteGroupWithElements
import com.a.vocabulary15.domain.usecases.DeleteGroupWithElementsImpl
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeleteGroupWithElementsTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var deleteGroupWithElements: DeleteGroupWithElements

    @Before
    fun setup(){
        deleteGroupWithElements = DeleteGroupWithElementsImpl(repository)
    }

    @Test
    fun `delete group with elements`() {
        runBlocking {
            deleteGroupWithElements.invoke(12)

            verify(repository).deleteGroup(12)
        }
    }
}