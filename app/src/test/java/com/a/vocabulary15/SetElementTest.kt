package com.a.vocabulary15

import com.a.vocabulary15.data.repository.FakeRepository
import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.usecases.GetElements
import com.a.vocabulary15.domain.usecases.GetElementsImpl
import com.a.vocabulary15.domain.usecases.SetElement
import com.a.vocabulary15.domain.usecases.SetElementImpl
import com.google.common.truth.Truth
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

    private lateinit var fakeRepository: FakeRepository

    private lateinit var setFakeElement: SetElement

    private lateinit var getFakeElement: GetElements

    private val element = Element(1, 2, "image", "value", "link")

    @Before
    fun setup() {
        setElement = SetElementImpl(repository)
        fakeRepository = FakeRepository()
        getFakeElement = GetElementsImpl(fakeRepository)
        setFakeElement = SetElementImpl(fakeRepository)
    }

    @Test
    fun `set element with success`() {
        runBlocking {
            setElement.invoke(element)
            verify(repository).setElement(element)
        }
    }

    @Test
    fun `set group with successfully with fake repository`() = runBlocking {
        val thisElement = Element(
            1,
            1,
            "image",
            "value",
            "link"
        )
        setFakeElement.invoke(thisElement)

        val oldElements = getFakeElement.invoke(1)
        val newElements = setFakeElement.invoke(thisElement)

        Truth.assertThat(newElements).isNotEqualTo(oldElements)
    }
}