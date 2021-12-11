package com.a.vocabulary15.viewmodel

import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.usecases.*
import com.a.vocabulary15.presentation.element.ElementsViewModel
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
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

    val idGroup = 0
    val element = Element(
        0,
    3,
    "image",
    "value",
    "link")

    @Before
    fun setup() {
        elementsViewModel = ElementsViewModel(
            getElements,
            setElement,
            deleteElement,
            editElement,
            deleteAll)
    }


    @Test
    fun `get element successful`(){
        runBlocking {
            elementsViewModel.getElements(idGroup)

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
}