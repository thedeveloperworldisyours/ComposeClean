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
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first

@RunWith(MockitoJUnitRunner::class)
class DeleteElementTest {

    @Mock
    private lateinit var repository: Repository
    private lateinit var deleteElement: DeleteElement



    @Before
    fun setup(){
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
}