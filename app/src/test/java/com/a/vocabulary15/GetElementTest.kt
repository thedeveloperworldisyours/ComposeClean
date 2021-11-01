package com.a.vocabulary15

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.usecases.GetElement
import com.a.vocabulary15.domain.usecases.GetElementImpl
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetElementTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var getElement: GetElement

    @Before
    fun setup(){
        getElement = GetElementImpl(repository)
    }

    @Test
    fun `get element with success`(){
        runBlocking {
            getElement.invoke()

            verify(repository).getElement()
        }
    }
}