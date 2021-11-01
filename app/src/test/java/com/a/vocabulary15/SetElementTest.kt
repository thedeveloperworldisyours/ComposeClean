package com.a.vocabulary15

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.usecases.SetElement
import com.a.vocabulary15.domain.usecases.SetElementImpl
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetElementTest {


    @Mock
    private lateinit var repository: Repository

    private lateinit var setElement: SetElement

    val element= Element(1, 2, "image","value","link")

    @Before
    fun setup() {
        setElement = SetElementImpl(repository)
    }

    @Test
    fun `set element with success`() {
        runBlocking {
            setElement.invoke(element)
            verify(repository).setElement(element)
        }
    }
}