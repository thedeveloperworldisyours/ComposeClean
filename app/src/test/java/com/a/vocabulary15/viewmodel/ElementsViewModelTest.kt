package com.a.vocabulary15.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.usecases.*
import com.a.vocabulary15.presentation.common.ViewState
import com.a.vocabulary15.presentation.element.ElementsViewModel
import com.nhaarman.mockitokotlin2.verify
import  org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ElementsViewModelTest {

    @Mock
    private lateinit var getElements: GetElements

    @Mock
    private lateinit var setElement: SetElement

    @Mock
    private lateinit var deleteElement: DeleteElement

    @Mock
    private lateinit var editElement: EditElement

    @Mock
    private lateinit var deleteAll: DeleteGroupWithElements

    lateinit var elementsViewModel: ElementsViewModel

    private val idGroup = 0
    val element = Element(
        0,
    3,
    "image",
    "value",
    "link")
    @Before
    fun setup() {
        val savedStateHandle = SavedStateHandle().apply {
            set("idGroup", idGroup)
            set("elementName", "elementName")
        }
        runBlocking {
            elementsViewModel = ElementsViewModel(
                getElements,
                setElement,
                deleteElement,
                editElement,
                deleteAll,
                savedStateHandle)
        }

    }


   @Test
    fun `get element successful`(){
        runBlocking {
            val element = Element(
                123,
            idGroup,
             "image",
             "value",
             "link")
            val elements = mutableListOf<Element>()
            elements.add(element)
            `when`(getElements.invoke(idGroup)).thenReturn(flow { emit(elements) })

            elementsViewModel.getElements(idGroup)

            assertEquals(elementsViewModel.viewState.value, ViewState.Loading)
            verify(getElements).invoke(idGroup)
        }
    }

    @Test
    fun `set elements successful` () {
        runBlocking {
            elementsViewModel.setElement(element)

            verify(setElement).invoke(element)
        }
    }

    @Test
    fun `delete element successful` (){
        runBlocking {
            elementsViewModel.deleteElement(element.id)

            verify(deleteElement).invoke(element.id)
        }
    }

    @Test
    fun `delete element and group`() {
        runBlocking {
            elementsViewModel.deleteGroupWithElements(element.groupId!!)

            verify(deleteAll).invoke(element.groupId!!)
        }
    }

    @Test
    fun `edit element` () {
        runBlocking {
            elementsViewModel.editElement(element)

            verify(editElement).invoke(element)
        }
    }
}