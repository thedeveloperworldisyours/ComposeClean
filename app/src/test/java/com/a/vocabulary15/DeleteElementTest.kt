package com.a.vocabulary15

import com.a.vocabulary15.data.repository.FakeRepository
import com.a.vocabulary15.domain.Repository
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.usecases.*
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

    private lateinit var fakeRepository: FakeRepository

    private lateinit var deleteElement: DeleteElement

    private lateinit var getFakeElement: GetElements

    private lateinit var setFakeElement: SetElement

    private lateinit var deleteFakeElement : DeleteElement

    @Before
    fun setup(){
        fakeRepository = FakeRepository()
        getFakeElement = GetElementsImpl(fakeRepository)
        deleteFakeElement = DeleteElementImpl(fakeRepository)
        setFakeElement = SetElementImpl(fakeRepository)
        deleteElement = DeleteElementImpl(repository)

        val elements = mutableListOf<Element>()

        ('a'..'z').forEachIndexed {index, c ->
            elements.add(
                Element(
                    index,
                index,
            "image",
            "value",
            "link")
            )
        }

    }

    @Test
    fun `delete element with success` () {
        runBlocking {
            deleteElement.invoke(1)

            verify(repository).deleteElement(1)
        }
    }

    /*@Test
    fun `delete fake`()= runBlocking {
        val thisElement = Element(
            1,
            1,
            "image",
            "value",
            "link")
        setFakeElement.invoke(thisElement)
        val oldElements = getFakeElement.invoke(1)
        val newElements = deleteElement.invoke(1)


    }*/
}