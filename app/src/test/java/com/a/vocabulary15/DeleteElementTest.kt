package com.a.vocabulary15

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.usecases.DeleteElement
import com.a.vocabulary15.domain.usecases.DeleteElementImpl
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeleteElementTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var deleteElement: DeleteElement

    @Before
    fun setup(){
        deleteElement = DeleteElementImpl(repository)
    }

    @Test
    fun `delete element with success` () {
        runBlocking {
            deleteElement.invoke(1)

            verify(repository).deleteElement(1)
        }
    }
}