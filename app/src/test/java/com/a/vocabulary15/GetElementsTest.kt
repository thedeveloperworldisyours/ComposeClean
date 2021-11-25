package com.a.vocabulary15

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.usecases.GetElementImpl
import com.a.vocabulary15.domain.usecases.GetElements
import com.a.vocabulary15.domain.usecases.GetElementsImpl
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetElementsTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var getElements: GetElements

    private var idGroup = 2
    @Before
    fun setup(){
        getElements = GetElementsImpl(repository)
    }

    @Test
    fun `get element with success`(){
        runBlocking {
            getElements.invoke(idGroup)

            verify(repository).getElements(idGroup)
        }
    }
}