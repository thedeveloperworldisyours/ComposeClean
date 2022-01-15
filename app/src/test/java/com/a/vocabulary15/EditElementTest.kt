package com.a.vocabulary15

import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.usecases.EditElement
import com.a.vocabulary15.domain.usecases.EditElementImpl
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EditElementTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var editElement: EditElement

    private val element = Element(1, 2, "image","value","link")

    @Before
    fun setup() {
        editElement = EditElementImpl(repository)
    }

    @Test
    fun `set element with success`() {
        runBlocking {
            editElement.invoke(element)
            verify(repository).editElement(element)
        }
    }
}