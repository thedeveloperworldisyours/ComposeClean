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
import kotlinx.coroutines.flow.first

@RunWith(MockitoJUnitRunner::class)
class GetElementsTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var fakeRepository: FakeRepository

    private lateinit var getElements: GetElements

    private lateinit var getFakeElements : GetElements

    private lateinit var setFakeElement: SetElement

    private var idGroup = 1

    @Before
    fun setup(){
        getElements = GetElementsImpl(repository)
        fakeRepository = FakeRepository()
        getFakeElements = GetElementsImpl(fakeRepository)
        setFakeElement = SetElementImpl(fakeRepository)

        val thisElement = Element(
            1,
            idGroup,
            "image",
            "value",
            "link"
        )
        runBlocking {
            fakeRepository.setElement(thisElement)
        }
    }

    @Test
    fun `get element with success`(){
        runBlocking {
            getElements.invoke(idGroup)

            verify(repository).getElements(idGroup)
        }
    }

    @Test
    fun `get element`() = runBlocking {
        val elements = getFakeElements.invoke(idGroup).first()

        Truth.assertThat(elements).isNotEqualTo(0)
    }
}